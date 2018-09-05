package com.bottools.botcontentfiller.ui.editmap

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.ui.biomes.ActivityEditBiomes
import com.bottools.botcontentfiller.ui.biomes.BiomesListFragment
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditBooleanView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import com.bottools.botcontentfiller.utils.getRandItem
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditMapTileFragment : Fragment(), AbstractChild.ChangeListener {

    companion object {

        private const val TILE ="tile"

        fun createInstance(tile: MapTile): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putSerializable(TILE, tile)
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var tile: MapTile

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        tile = arguments?.getSerializable(TILE) as MapTile
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.from_biome_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.from_biome-> {
                openBiomesFrament()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openBiomesFrament() {
        val fragment = BiomesListFragment()
        fragment.listener = object : BiomesListFragment.BiomeChoosedListener {
            override fun biomeChoosed(biome: Biome) {
                fillFromBiome(biome)
            }
        }
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.fragment_container, fragment, ActivityEditBiomes.FRAGMENT_TAG)
        transaction.commit()
    }

    private fun fillFromBiome(biome: Biome) {
        val randTile = biome.tiles.getRandItem()
        tile.biomeId = biome.id
        randTile?.let { randTile ->
            tile.thisTileCustomDescription = randTile.thisTileCustomDescription
            tile.nextTileCustomDescription= randTile.nextTileCustomDescription
            tile.customFarBehindText = randTile.customFarBehindText
            tile.isUnpassable = randTile.isUnpassable
            tile.canSeeThrow = randTile.canSeeThrow
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!

        val views = ArrayList<AbstractChild>()
        views.add(EditSingleStringView(tile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context))
        views.add(EditSingleStringView(tile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context))
        views.add(EditSingleStringView(tile.customFarBehindText, context.getString(R.string.far_behind_descr), context))
        views.add(EditBooleanView(tile.isUnpassable, context.getString(R.string.unpassable), context))
        views.add(EditBooleanView(tile.canSeeThrow, context.getString(R.string.can_see_throw), context))
        views.forEach {
            it.changeListener = this
            it.attachToView(container)
        }
    }

    override fun onChange() {
        tile.biomeId = 0
    }
}