package com.bottools.botcontentfiller.ui.editmap

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.WorldMap
import com.google.gson.Gson
import java.io.*


class ActivityEditMap : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag"
    }

    var map: com.bottools.botcontentfiller.model.WorldMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map)
        openOrCreateMap()
        val fragment = EditMapFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        transaction.commit()
    }

    private fun openOrCreateMap() {
        val loadMap = DatabaseManager.loadMap()
        if (loadMap == null) {
            map = WorldMap()
            if (map!!.tiles.isEmpty()) {
                map!!.initMap(20, 20)
            }
            DatabaseManager.saveMap(map!!)
        } else {
            map = loadMap
        }
    }

    override fun onPause() {
        super.onPause()
        DatabaseManager.saveMap(map!!)
    }

}
