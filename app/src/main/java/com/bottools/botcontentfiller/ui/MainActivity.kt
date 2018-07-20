package com.bottools.botcontentfiller.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.ui.edit_map.ActivityEditMap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        editMapButton.setOnClickListener {
            startActivity(Intent(this, ActivityEditMap::class.java))
        }
    }
}
