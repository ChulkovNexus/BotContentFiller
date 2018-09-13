package com.bottools.botcontentfiller.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.ExportObject
import com.bottools.botcontentfiller.ui.biomes.ActivityEditBiomes
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.events.ActivityEditEvents
import com.bottools.botcontentfiller.ui.worktype.ActivityEditWorkTypes
import com.google.gson.Gson
import io.realm.ObjectServerError
import io.realm.Realm
import io.realm.SyncCredentials
import io.realm.SyncUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXP_REQ = 0x001
        private const val IMP_REQ = 0x002
        private val INSTANCE_ADDRESS = "handmadebotfillercloud.de1a.cloud.realm.io"
        val AUTH_URL = "https://$INSTANCE_ADDRESS/auth"

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
        connectToRealmCloud()
    }

    private fun connectToRealmCloud() {
        showProgress(true)
        val credentials = SyncCredentials.nickname("valera77", true)
        SyncUser.logInAsync(credentials, AUTH_URL, object : SyncUser.Callback<SyncUser> {
            override fun onSuccess(user: SyncUser) {
                showProgress(false)
                val build = user.createConfiguration("/default").fullSynchronization().build()
                Realm.setDefaultConfiguration(build)
            }

            override fun onError(error: ObjectServerError?) {
                showProgress(false)
                Toast.makeText(baseContext, "Uh oh something went wrong! (check your logcat please)", Toast.LENGTH_LONG).show()
                error?.printStackTrace()
            }
        })
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXP_REQ)
                } else {
                    export()
                }
                true
            }
            R.id.action_import-> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), IMP_REQ)
                } else {
                    actionImport()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        }
    }

    private fun actionImport() {
        val exportObject = ExportObject()
        exportObject.map = DatabaseManager.loadMap()
        exportObject.biomes = DatabaseManager.getList()
        exportObject.events = DatabaseManager.getList()
        exportObject.workTypes = DatabaseManager.getList()
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
