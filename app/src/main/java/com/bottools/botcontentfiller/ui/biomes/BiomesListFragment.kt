package com.bottools.botcontentfiller.ui.biomes

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ListView
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.ui.edit_map.ActivityEditMap

class BiomesListFragment : ListFragment() {

    var listener: BiomeChoosedListener? = null
    var biomes = ArrayList<Biome>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = BiomesAdapter(activity!!, {
            openBiomeTilesListFragment(it)
        })
        adapter.addAll(biomes)
        listAdapter = adapter
    }

    private fun openBiomeTilesListFragment(biome: Biome) {
        val fragment = BiomeTilesListFragment.createInstance(biome)
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