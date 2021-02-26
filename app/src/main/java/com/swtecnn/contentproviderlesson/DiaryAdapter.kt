package com.swtecnn.contentproviderlesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swtecnn.contentproviderlesson.db.DiaryEntry

class DiaryAdapter: RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    private val items: ArrayList<DiaryEntry> = ArrayList()

    fun setItems(items: ArrayList<DiaryEntry>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class DiaryViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val diary_text_view: TextView= item.findViewById(R.id.contact_name_text_view)

        fun bind(diaryEntry: DiaryEntry){
            diary_text_view.text = diaryEntry.entryText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_item_layout, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diaryItem: DiaryEntry = items.get(position)
        holder.bind(diaryItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}