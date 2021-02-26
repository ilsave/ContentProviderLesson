package com.swtecnn.contentproviderlesson.content_providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.swtecnn.contentproviderlesson.db.AppDatabase
import com.swtecnn.contentproviderlesson.db.DiaryEntry
import com.swtecnn.contentproviderlesson.db.DiaryEntryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DbContentProvider: ContentProvider() {

    companion object {
        const val AUTHORITY = "com.swtecnn.contentproviderlesson.DbContentProvider"
        private val DIARY_ENTRY_TABLE = "DiaryEntry"
        val DIARY_TABLE_CONTENT_URI: Uri = Uri.parse("content://" +
        AUTHORITY + "/" + DIARY_ENTRY_TABLE)
    }

    private val DIARY_ENTRIES = 1
    private val DIARY_ENTRY_ID = 2
    private var diaryEntryDao: DiaryEntryDao? = null
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sUriMatcher.addURI(AUTHORITY, DIARY_ENTRY_TABLE, DIARY_ENTRIES)
        sUriMatcher.addURI(AUTHORITY, "$DIARY_ENTRY_TABLE/#", DIARY_ENTRY_ID)
    }


    override fun onCreate(): Boolean {
        diaryEntryDao = context?.let { AppDatabase.getInstance(it).diaryEntryDao() }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val uriType = sUriMatcher.match(uri)
        var cursor: Cursor? = null
        when(uriType){
            DIARY_ENTRY_ID -> {
                val id: Long? = uri.lastPathSegment?.toLong()
                if (id != null) {
                    cursor = diaryEntryDao?.getById(id)!!
                }
            }
            DIARY_ENTRIES -> {
                return diaryEntryDao?.getAll()
            }
            else -> throw IllegalArgumentException("Uknown Uri")
        }
        return cursor
    }

    override fun getType(uri: Uri): String {
        return "text"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val uriType = sUriMatcher.match(uri)
        val id: Long?
        when (uriType) {
            DIARY_ENTRIES -> {
                val newEntry = DiaryEntry.fromContentValues(values)
                id = newEntry?.let {
                    diaryEntryDao?.addEntry(it) }
            }
            else -> throw UnsupportedOperationException()
        }
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.parse("$DIARY_ENTRY_TABLE/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val uriType = sUriMatcher.match(uri)
        val id: Long?
        when (uriType) {
            DIARY_ENTRIES -> {
                var deletedItem = selectionArgs?.get(0)
                id = uri.lastPathSegment?.toLong()
                var cursor = id?.let { diaryEntryDao?.getById(it) }
                if (cursor?.moveToNext()!!){
                    val textEntry = cursor.getString(cursor.getColumnIndex("entry_text"))
                    val dateText = cursor.getString(cursor.getColumnIndex("entry_date"))
                    val diaryEntryDeleted = DiaryEntry(id!!.toInt(), textEntry, dateText)
                    diaryEntryDao?.delete(diaryEntryDeleted)
                }
            }
            else -> throw UnsupportedOperationException()
        }
        context?.contentResolver?.notifyChange(uri, null)
        return 1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val uriType = sUriMatcher.match(uri)
        val id: Long?
        when (uriType) {
            DIARY_ENTRIES -> {
                id = uri.lastPathSegment?.toLong()
                val newEntry = DiaryEntry.fromContentValues(values)!!
                diaryEntryDao?.update(DiaryEntry(id!!.toInt(),newEntry.entryText, newEntry.entryDate))
            }
            else -> throw UnsupportedOperationException()
        }
        return values?.size()!!
    }
}