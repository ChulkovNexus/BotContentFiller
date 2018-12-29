package com.bottools.botcontentfiller.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.manager.http.ErrorEntity
import com.bottools.botcontentfiller.manager.http.HandlerCallback
import com.bottools.botcontentfiller.manager.http.HttpClient
import com.bottools.botcontentfiller.model.ExportObject
import com.bottools.botcontentfiller.ui.biomes.ActivityEditBiomes
import com.bottools.botcontentfiller.ui.buildings.ActivityEditBuildings
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.events.ActivityEditEvents
import com.bottools.botcontentfiller.ui.items.ActivityEditItems
import com.bottools.botcontentfiller.ui.map_resources.ActivityEditMapResources
import com.bottools.botcontentfiller.ui.worktype.ActivityEditWorkTypes
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.ResponseBody
import java.io.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXP_REQ = 0x001
        private const val IMP_REQ = 0x002

    }
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
        editEvents.setOnClickListener {
            startActivity(Intent(this, ActivityEditEvents::class.java))
        }
        editWorkTipes.setOnClickListener {
            startActivity(Intent(this, ActivityEditWorkTypes::class.java))
        }
        editBuildings.setOnClickListener {
            startActivity(Intent(this, ActivityEditBuildings::class.java))
        }
        editItems.setOnClickListener {
            startActivity(Intent(this, ActivityEditItems::class.java))
        }
        editItems.setOnClickListener {
            startActivity(Intent(this, ActivityEditMapResources::class.java))
        }
    }

    private fun showProgress(show: Boolean) {
        progress.visibility = if (show) View.VISIBLE else View.GONE
        body.visibility = if (!show) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export-> {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXP_REQ)
//                } else {
//                    export()
//                })
                postData()
                true
            }
            R.id.action_import-> {
                getData()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), IMP_REQ)
//                } else {
//                    actionImport()
//                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData() {
        showProgress(true)
        HttpClient.getData(object : HandlerCallback<ExportObject>(){
            override fun onError(error: ErrorEntity?) {
                Log.d("12354", error.toString())
            }

            override fun onComplite(exportObject: ExportObject?) {
                showProgress(false)
                saveExportOpject(exportObject)
            }

        })
    }

    private fun postData() {
        showProgress(true)
        val exportObject = ExportObject()
        exportObject.map = DatabaseManager.loadMap()
        exportObject.biomes = DatabaseManager.getList()
        exportObject.events = DatabaseManager.getList()
        exportObject.workTypes = DatabaseManager.getList()
        exportObject.items = DatabaseManager.getList()
        exportObject.buildings = DatabaseManager.getList()
        exportObject.mapResources = DatabaseManager.getList()
        HttpClient.postData(exportObject, object : HandlerCallback<ResponseBody>(){
            override fun onError(error: ErrorEntity?) {
                Log.d("12354", error.toString())
            }

            override fun onComplite(result: ResponseBody?) {
                showProgress(false)
                Log.d("12354", result!!.string())
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            IMP_REQ -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    actionImport()
                }
                return
            }
            EXP_REQ -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    export()
                }
                return
            }
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
        saveExportOpject(exportObject)
    }

    fun saveExportOpject(exportObject: ExportObject?) {
        if (exportObject != null) {
            exportObject.map?.let {
                DatabaseManager.saveMap(it)
            }
            exportObject.biomes?.forEach {
                DatabaseManager.save(it)
            }
            exportObject.events?.forEach {
                DatabaseManager.save(it)
            }
            exportObject.workTypes?.forEach {
                DatabaseManager.save(it)
            }
            exportObject.items?.forEach {
                DatabaseManager.save(it)
            }
            exportObject.buildings?.forEach {
                DatabaseManager.save(it)
            }
            exportObject.mapResources?.forEach {
                DatabaseManager.save(it)
            }
        }
    }

    private fun actionImport() {
        val exportObject = ExportObject()
        exportObject.map = DatabaseManager.loadMap()
        exportObject.biomes = DatabaseManager.getList()
        exportObject.events = DatabaseManager.getList()
        exportObject.workTypes = DatabaseManager.getList()
        exportObject.items = DatabaseManager.getList()
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
