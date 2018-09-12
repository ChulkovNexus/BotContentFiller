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

        private const val TILE = "tile"

        fun createInstance(tile: MapTile): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putSerializable(TILE, tile)
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var tile: MapTile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        val mapTile = arguments?.getSerializable(TILE) as MapTile
        tile = (activity as ActivityEditMap).map!!.getTile(mapTile.posX, mapTile.posY)
        activity!!.title = getString(R.string.tile_filling)
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
            R.id.from_biome -> {
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
        tile.biomeId = biome.id
        val randTile = biome.tiles.getRandItem()
        randTile?.let { randTile ->
            tile.thisTileCustomDescription = randTile.thisTileCustomDescription
            tile.nextTileCustomDescription = randTile.nextTileCustomDescription
            tile.customFarBehindText = randTile.customFarBehindText
            tile.isUnpassable = randTile.isUnpassable
            tile.canSeeThrow = randTile.canSeeThrow
        }
    }

    private lateinit var element: EditSingleStringView
    private lateinit var element1: EditSingleStringView
    private lateinit var element2: EditSingleStringView
    private lateinit var element3: EditBooleanView
    private lateinit var element4: EditBooleanView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!

        val views = ArrayList<AbstractChild>()
        element = EditSingleStringView(tile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context)
        element1 = EditSingleStringView(tile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context)
        element2 = EditSingleStringView(tile.customFarBehindText, context.getString(R.string.far_behind_descr), context)
        element3 = EditBooleanView(tile.isUnpassable, context.getString(R.string.unpassable), context)
        element4 = EditBooleanView(tile.canSeeThrow, context.getString(R.string.can_see_throw), context)
        views.add(element)
        views.add(element1)
        views.add(element2)
        views.add(element3)
        views.add(element4)
        views.forEach {
            it.changeListener = this
            it.attachToView(container)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(tile) {
            tile.thisTileCustomDescription = element.str
            tile.nextTileCustomDescription = element1.str
            tile.customFarBehindText = element2.str
            tile.isUnpassable = element3.boolean
            tile.canSeeThrow = element4.boolean
        }
    }

    override fun onChange() {
        tile.editedAfterBiomeSetted = true
    }
}