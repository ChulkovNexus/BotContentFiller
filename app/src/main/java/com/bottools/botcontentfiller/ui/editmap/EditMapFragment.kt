package com.bottools.botcontentfiller.ui.editmap

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.logic.viewver.EarthTypeViewer
import com.bottools.botcontentfiller.logic.viewver.Viewer
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_map.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class EditMapFragment : Fragment() {

    var map: com.bottools.botcontentfiller.model.WorldMap? = null
    private var currentPositionX = 0
    private var currentPositionY = 0
    private lateinit var viewer : Viewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val ac = activity as ActivityEditMap
        val localMap = ac.map
        map = localMap
        if (localMap!=null) {
            viewer = EarthTypeViewer(localMap)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_map, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save_map -> {
                saveMap()
                true
            }
            R.id.action_edit_map_defaults -> {
                openEditMapDefaultsFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openEditMapDefaultsFragment() {
        val fragment = EditMapDefaultsFragment()
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }

    private fun saveMap() {
        val gson = Gson()
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "TestMap.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        val json = gson.toJson(map)
        try {
            val fileOutputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(json)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.title = "0:0"
        positionIndicator.setSize(map!!)
        edit.setOnClickListener {
            val fragment = EditMapTileFragment.createInstance(map!!.getTile(currentPositionX, currentPositionY))
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.addToBackStack("")
            transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
        }
        updateTexts()
        go_top.setOnClickListener {
            if (currentPositionY > 0) {
                currentPositionY--
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                appCompatActivity.supportActionBar?.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_bot.setOnClickListener {
            if (currentPositionY < map!!.tiles.size - 1) {
                currentPositionY++
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                appCompatActivity.supportActionBar?.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_right.setOnClickListener {
            if (currentPositionX < map!!.tiles[currentPositionY].size - 1) {
                currentPositionX++
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                appCompatActivity.supportActionBar?.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_left.setOnClickListener {
            if (currentPositionX >= 0) {
                currentPositionX--
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                appCompatActivity.supportActionBar?.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
    }

    private fun updateTexts() {
        val currentTile = map!!.getTile(currentPositionX, currentPositionY)
        custom_text.text = viewer.getCurrentTileText(currentTile)
        way_text.text = "${map!!.defaultLookingForWayPrefix} ${viewer.getTopText(currentTile)}, ${viewer.getRightText(currentTile)}," +
                " ${viewer.getBottomText(currentTile)}, ${viewer.getLeftText(currentTile)}"
    }

}