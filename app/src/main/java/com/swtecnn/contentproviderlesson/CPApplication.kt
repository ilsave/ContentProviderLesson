package com.swtecnn.contentproviderlesson

import android.app.Application
import android.os.AsyncTask
import com.swtecnn.contentproviderlesson.db.AppDatabase
import com.swtecnn.contentproviderlesson.db.DiaryEntry
import com.swtecnn.contentproviderlesson.db.DiaryEntryDao

class CPApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val diaryEntryDao: DiaryEntryDao = AppDatabase.getInstance(applicationContext).diaryEntryDao()

        doAsync {
            val diaryEntries: List<DiaryEntry> = diaryEntryDao.getAllNotCursor()
            val listOfEntries: ArrayList<DiaryEntry> = ArrayList()
            if (diaryEntries.isEmpty()) {
                for (i in 0..15) {
                    val diaryEntry = DiaryEntry(0, "Entry text number $i", "2021-02-2$i")
                    listOfEntries.add(diaryEntry)
                }
            }
            diaryEntryDao.addAll(listOfEntries)
        }.execute()
    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }
}