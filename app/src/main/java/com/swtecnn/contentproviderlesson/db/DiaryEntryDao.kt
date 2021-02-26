package com.swtecnn.contentproviderlesson.db

import android.database.Cursor
import androidx.room.*

@Dao
interface DiaryEntryDao {
    @Query("SELECT id, entry_text, entry_date from DiaryEntry")
    fun getAll(): Cursor

    @Query("SELECT id, entry_text, entry_date from DiaryEntry where id = :id")
    fun getById(id: Long): Cursor

    @Query("select * from DiaryEntry")
    fun getAllNotCursor(): List<DiaryEntry>

    @Delete
    fun delete(entry: DiaryEntry): Int

    @Update
    fun update(entry: DiaryEntry): Int

    @Insert
    fun addEntry(entry: DiaryEntry): Long?

    @Insert
    fun addAll(entries: List<DiaryEntry>)
}