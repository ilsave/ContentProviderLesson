package com.swtecnn.contentproviderlesson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audio_provider_btn: Button = findViewById(R.id.audio_list_btn)
        val db_prodiver_btn: Button = findViewById(R.id.db_provider_btn)
        val contacts_provider_button: Button = findViewById(R.id.contact_list_btn)

        audio_provider_btn.setOnClickListener {
            val intent = Intent(this, AudioListActivity::class.java)
            startActivity(intent)
        }

        db_prodiver_btn.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }
    }
}