package com.bottools.botcontentfiller.ui.biomes

import android.R
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.editmap.EditMapTileFragment
import com.bottools.botcontentfiller.utils.random

class BiomeTilesListFragment : ListFragment() {

    companion object {
        private const val BIOME_ID ="biome_id"

        fun createInstance(biomeId: Int): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putInt(BIOME_ID, biomeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter :BiomeTilesAdapter
    private var biome : Biome? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val biomeId = arguments?.getInt(BIOME_ID)
        if (biomeId != null) {
            biome = DatabaseManager.getBiome(biomeId)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        biome?.let { biome ->
            adapter = BiomeTilesAdapter(activity!!, { biomeTile->
                openBiomTileFragment(biomeTile)
            }, { biomeTile->
                removeTile(biomeTile)
            })
            adapter.addAll(biome.tiles.filter { !TextUtils.isEmpty(it.thisTileCustomDescription) })
            listAdapter = adapter
        }
    }

    private fun removeTile(biomeTile: BiomeTile) {
        biome?.let { biome ->
            biome.tiles.remove(biomeTile)
            adapter.remove(biomeTile)
            DatabaseManager.removeBiomeTile(biomeTile)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(com.bottools.botcontentfiller.R.menu.plus_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            com.bottools.botcontentfiller.R.id.plus-> {
                addTile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addTile() {
        val biomeTile = BiomeTile()
        biomeTile.id = (0..Int.MAX_VALUE).random()
        DatabaseManager.saveBiomeTile(biomeTile)
        openBiomTileFragment(biomeTile)
    }

    private fun openBiomTileFragment(biomeTile: BiomeTile) {
        val fragment = EditBiomeTileFragment.createInstance(biomeTile.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}