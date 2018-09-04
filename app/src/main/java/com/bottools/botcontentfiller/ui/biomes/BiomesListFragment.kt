package com.bottools.botcontentfiller.ui.biomes

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.utils.random

class BiomesListFragment : ListFragment() {

    var listener: BiomeChoosedListener? = null
    private var biomes = ArrayList<Biome>()
    private lateinit var adapter : BiomesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        biomes = DatabaseManager.getBiomes()
        adapter = BiomesAdapter(activity!!, {
            openBiomeTilesListFragment(it)
        }, {
            removeBiome(it)
        })
        adapter.addAll(biomes)
        listAdapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.plus_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.plus-> {
                addBiome()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addBiome() {
        val biome = Biome()
        biome.id = (0..Int.MAX_VALUE).random()
        DatabaseManager.saveBiome(biome)
        openBiomeTilesListFragment(biome)
    }

    private fun removeBiome(biome: Biome) {
        biomes.remove(biome)
        adapter.remove(biome)
        adapter.notifyDataSetChanged()
        DatabaseManager.removeBiome(biome.id)
    }

    private fun openBiomeTilesListFragment(biome: Biome) {
        val fragment = EditBiomeFragment.createInstance(biome.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener?.biomeChoosed(biomes[position])
    }

    interface BiomeChoosedListener {
        fun biomeChoosed(biome: Biome)
    }
}