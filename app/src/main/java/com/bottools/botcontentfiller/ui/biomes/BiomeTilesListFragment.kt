package com.bottools.botcontentfiller.ui.biomes

import android.R
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.ui.edit_map.ActivityEditMap
import com.bottools.botcontentfiller.ui.edit_map.EditMapTileFragment

class BiomeTilesListFragment : ListFragment() {

    companion object {
        private const val BIOME ="biome"

        fun createInstance(biome: Biome): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putSerializable(BIOME, biome)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var biome : Biome
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biome = arguments?.getSerializable(BIOME) as Biome
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            val adapter = ArrayAdapter(activity, R.layout.simple_list_item_1, biome.tiles.filter { !TextUtils.isEmpty(it.thisTileCustomDescription) }
                    .map { it.thisTileCustomDescription })
            listAdapter = adapter
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val biomeTile = biome.tiles.filter { !TextUtils.isEmpty(it.thisTileCustomDescription) }[position]
        val fragment = EditBiomeTileFragment.createInstance(biomeTile)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}