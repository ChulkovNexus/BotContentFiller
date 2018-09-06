package com.bottools.botcontentfiller.ui

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.ExportObject
import com.bottools.botcontentfiller.ui.biomes.ActivityEditBiomes
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        editMapButton.setOnClickListener {
            startActivity(Intent(this, ActivityEditMap::class.java))
        }
        editBiomes.setOnClickListener {
            startActivity(Intent(this, ActivityEditBiomes::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export-> {
                export()
                true
            }
            R.id.action_import-> {
                actionImport()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun export() {
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "TestMap.json")
        var exportObject : ExportObject? = null
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
            exportObject = gson.fromJson(outStringBuf.toString(), ExportObject::class.java)
        }
        if (exportObject != null) {
            exportObject.map?.let {
                DatabaseManager.saveMap(it)
            }
            exportObject.biomes?.forEach {
                DatabaseManager.saveBiome(it)
            }
        }
    }

    private fun actionImport() {
        val exportObject = ExportObject()
        exportObject.map = DatabaseManager.loadMap()
        exportObject.biomes = DatabaseManager.getBiomes()
        val gson = Gson()
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "TestMap.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        val json = gson.toJson(exportObject)
        try {
            val fileOutputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(json)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Toast.makeText(this, "File write failed: " + e.toString(), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
