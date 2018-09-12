package com.bottools.botcontentfiller.ui.biomes.biomtiles

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.ui.biomes.adapters.BiomeTilesAdapter
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.utils.random

class BiomeTilesListFragment : ListFragment() {

    companion object {
        private const val BIOME_ID = "biome_id"

        fun createInstance(biomeId: Int): BiomeTilesListFragment {
            val fragment = BiomeTilesListFragment()
            val bundle = Bundle()
            bundle.putInt(BIOME_ID, biomeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter : BiomeTilesAdapter
    private var biome : Biome? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.biome_tiles_list)
        val biomeId = arguments?.getInt(BIOME_ID)
        if (biomeId != null) {
            biome = DatabaseManager.getBiome(biomeId)
        }
        biome?.let { biome ->
            adapter = BiomeTilesAdapter(activity!!, { biomeTile ->
                openBiomTileFragment(biomeTile)
            }, { biomeTile ->
                removeTile(biomeTile)
            })
            adapter.addAll(biome.tiles)
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
        biome?.let {
            val biomeTile = BiomeTile()
            biomeTile.id = (0..Int.MAX_VALUE).random()
            it.tiles.add(biomeTile)
            DatabaseManager.saveBiomeTile(biomeTile)
            DatabaseManager.saveBiome(it)
            openBiomTileFragment(biomeTile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        biome?.let { biome ->
//            DatabaseManager.saveBiome(biome)
//        }
    }

    private fun openBiomTileFragment(biomeTile: BiomeTile) {
        val fragment = EditBiomeTileFragment.createInstance(biomeTile.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}