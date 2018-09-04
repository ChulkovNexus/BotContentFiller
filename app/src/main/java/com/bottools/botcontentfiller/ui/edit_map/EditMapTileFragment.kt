package com.bottools.botcontentfiller.ui.edit_map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditBooleanView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditMapTileFragment : Fragment() {


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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        
        val views = ArrayList<AbstractChild>()
        views.add(EditSingleStringView(tile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context))
        views.add(EditSingleStringView(tile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context))
        views.add(EditSingleStringView(tile.customFarBehindText, context.getString(R.string.far_behind_descr), context))
        views.add(EditBooleanView(tile.isUnpassable, context.getString(R.string.unpassable), context))
        views.add(EditBooleanView(tile.canSeeThrow, context.getString(R.string.can_see_throw), context))
        views.forEach {
            it.attachToView(container)
        }
    }
}