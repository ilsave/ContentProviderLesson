package com.swtecnn.contentproviderlesson.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DiaryEntry::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun diaryEntryDao(): DiaryEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabase(context).also { INSTANCE = it }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cp_lesson_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}