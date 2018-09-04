package com.bottools.botcontentfiller.ui.biomes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.ui.edit_map.EditMapTileFragment
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditBooleanView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditPercentView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditBiomeTileFragment : Fragment() {

    private lateinit var biomeTile : BiomeTile


    companion object {

        private const val TILE ="tile"
        fun createInstance(tile: BiomeTile): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putSerializable(TILE, tile)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biomeTile = arguments?.getSerializable(TILE) as BiomeTile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        view.findViewById<View>(R.id.edit_unpassable_layout).visibility = View.GONE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val context = context!!

        val views = ArrayList<AbstractChild>()
        views.add(EditSingleStringView(biomeTile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context))
        views.add(EditSingleStringView(biomeTile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context))
        views.add(EditSingleStringView(biomeTile.customFarBehindText, context.getString(R.string.far_behind_descr), context))
        views.add(EditBooleanView(biomeTile.isUnpassable, context.getString(R.string.unpassable), context))
        views.add(EditBooleanView(biomeTile.canSeeThrow, context.getString(R.string.can_see_throw), context))
        views.add(EditPercentView(biomeTile.probability, context.getString(R.string.probability), context))
        views.forEach {
            it.attachToView(container)
        }
    }
}