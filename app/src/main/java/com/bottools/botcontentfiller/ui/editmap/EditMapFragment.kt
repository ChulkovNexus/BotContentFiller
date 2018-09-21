package com.bottools.botcontentfiller.ui.editmap

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.logic.viewver.EarthTypeViewer
import com.bottools.botcontentfiller.logic.viewver.Viewer
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.utils.getRandItem
import kotlinx.android.synthetic.main.fragment_edit_map.*

class EditMapFragment : Fragment() {

    var map: com.bottools.botcontentfiller.model.WorldMap? = null
    private var currentPositionX = 0
    private var currentPositionY = 0
    private lateinit var viewer : Viewer
    private lateinit var biomesList: ArrayList<Biome>
    private var selectedBiome : Biome? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        biomesList = DatabaseManager.getList()
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
        inflater.inflate(R.menu.menu_edit_map, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_edit_map_defaults -> {
                openEditMapDefaultsFragment()
                true
            }
//            R.id.action_fill_random_from_biome-> {
//                fillRandomFromBiome()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fillFromBiome() {
        if (selectedBiome!=null) {
            val currentTile = map!!.getTile(currentPositionX, currentPositionY)
            currentTile.fillFromBiome(selectedBiome!!)
        } else {
            Toast.makeText(context, R.string.select_biome_before, Toast.LENGTH_LONG).show()
        }
    }

    private fun openEditMapDefaultsFragment() {
        val fragment = EditMapDefaultsFragment()
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "$currentPositionX:$currentPositionY"
        positionIndicator.biomes.clear()
        positionIndicator.biomes.addAll(biomesList)
        positionIndicator.setSize(map!!)
        positionIndicator.setPosition(currentPositionX, currentPositionY)
        edit.setOnClickListener {
            val fragment = EditMapTileFragment.createInstance(map!!.getTile(currentPositionX, currentPositionY))
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.addToBackStack("")
            transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
        }
        fillFromBiome.setOnClickListener {
            fillFromBiome()
        }

        biomes_spinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, biomesList.map { it.name })
        biomes_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedBiome = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBiome = biomesList[position]
            }

        }
        updateTexts()
        go_top.setOnClickListener {
            if (currentPositionY > 0) {
                currentPositionY--
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                activity!!.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_bot.setOnClickListener {
            if (currentPositionY < map!!.tiles.size - 1) {
                currentPositionY++
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                activity!!.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_right.setOnClickListener {
            if (currentPositionX < map!!.tiles[currentPositionY].size - 1) {
                currentPositionX++
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                activity!!.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
        go_left.setOnClickListener {
            if (currentPositionX > 0) {
                currentPositionX--
                positionIndicator.setPosition(currentPositionX, currentPositionY)
                activity!!.title = "$currentPositionX:$currentPositionY"
                updateTexts()
            }
        }
    }

    private fun updateTexts() {
        val currentTile = map!!.getTile(currentPositionX, currentPositionY)
        custom_text.text = viewer.getCurrentTileText(currentTile)
        way_text.text = "${map!!.defaultLookingForWayPrefix.getRandItem()} ${viewer.getTopText(currentTile)}, ${viewer.getRightText(currentTile)}," +
                " ${viewer.getBottomText(currentTile)}, ${viewer.getLeftText(currentTile)}"
    }

}