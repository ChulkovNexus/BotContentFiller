package com.bottools.botcontentfiller.ui.edit_map

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R
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
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "TestMap.json")
        if (!file.exists()) {
            file.createNewFile()
        } else {
            val outStringBuf = StringBuffer()
            val fIn = FileInputStream(file)
            val isr = InputStreamReader(fIn)
            val inBuff = BufferedReader(isr)
            var inputLine = inBuff.readLine()
            while (inputLine != null) {
                outStringBuf.append(inputLine)
                outStringBuf.append("\n")
                inputLine = inBuff.readLine()
            }
            inBuff.close()
            val gson = Gson()
            map = gson.fromJson(outStringBuf.toString(), WorldMap::class.java)
        }
        if (map == null) {
            map = WorldMap()
            if (map!!.tiles.isEmpty()) {
                map!!.initMap(20, 20)
            }
        }
    }
}
