package com.swtecnn.contentproviderlesson.db

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "DiaryEntry")
class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "entry_text") var entryText: String?,
    @ColumnInfo(name = "entry_date") var entryDate: String?
)
{
    companion object {
        fun fromContentValues(contentValues: ContentValues?): DiaryEntry? {
            if (contentValues == null){
                return null
            }
            var diaryEntry: DiaryEntry? = null
            if (contentValues.containsKey("entry_text") && contentValues.containsKey("entry_date")) {
                diaryEntry = DiaryEntry(0, "", "")
                diaryEntry.entryText = contentValues.getAsString("entry_text")
                diaryEntry.entryDate = contentValues.getAsString("entry_date")
            }
            return diaryEntry
        }
    }
}