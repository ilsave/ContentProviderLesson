package com.swtecnn.contentproviderlesson

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/**
 * A simple [Fragment] subclass.
 * Use the [AudioListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioListFragment : Fragment() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_audio_list, container, false)
        contactsRecyclerView = view.findViewById(R.id.contacts_recycler_view)
        contactsAdapter = ContactsAdapter()
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        contactsRecyclerView.layoutManager = layoutManager
        contactsRecyclerView.adapter = contactsAdapter
        fillAdapter()
        return view
    }

    fun fillAdapter() {
        val cursor = activity?.contentResolver?.query(
            MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null,
            null, null)
        val audioList = ArrayList<ContactItem>()
        cursor?.use {
            while(it.moveToNext()){
                val audioName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                audioList.add(ContactItem(audioName))
            }
        }
        contactsAdapter.setItems(audioList)
        cursor?.close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = AudioListFragment()

    }
}