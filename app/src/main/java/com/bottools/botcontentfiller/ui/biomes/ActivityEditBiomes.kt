package com.bottools.botcontentfiller.ui.biomes

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.WorldMap
import com.google.gson.Gson
import java.io.*


class ActivityEditBiomes : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map)
        val fragment = BiomesListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        transaction.commit()
    }
}
