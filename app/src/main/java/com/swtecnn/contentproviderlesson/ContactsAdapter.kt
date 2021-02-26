package com.swtecnn.contentproviderlesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    private var items: ArrayList<ContactItem> = ArrayList()
    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val contactTextView = itemView.findViewById<TextView>(R.id.contact_name_text_view)

        fun bind(contactItem: ContactItem){
            contactTextView.text = contactItem.contactName
        }
    }

    fun setItems(items: ArrayList<ContactItem>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
       val view = LayoutInflater.from(parent.context)
               .inflate(R.layout.contacts_item_layout, parent, false)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contactItem = items.get(position)
        holder.bind(contactItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}