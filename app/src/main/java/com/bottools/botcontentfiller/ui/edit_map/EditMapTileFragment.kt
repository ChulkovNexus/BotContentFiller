package com.bottools.botcontentfiller.ui.edit_map

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditMapTileFragment : Fragment() {

    var map: com.bottools.botcontentfiller.model.WorldMap? = null

    companion object {

        const val POS_X = "posx"
        const val POS_Y = "posy"
        fun createInstance(posX: Int, posY: Int): EditMapTileFragment {
            val fragment = EditMapTileFragment()
            val bundle = Bundle()
            bundle.putInt(POS_X, posX)
            bundle.putInt(POS_Y, posY)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val ac = activity as ActivityEditMap
        map = ac.map
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val posx = arguments!!.getInt(POS_X)
        val posy = arguments!!.getInt(POS_Y)
        val listFiller = LayoutFiller(context, activity!!.supportFragmentManager)
        val localMap = map!!
        mergeNulls(localMap, localMap.tiles[posx][posy])


        is_unpassable.isChecked = localMap.tiles[posx][posy].isUnpassable
        is_unpassable.setOnCheckedChangeListener { view, isChecked ->
            localMap.tiles[posx][posy].isUnpassable = isChecked
        }

        can_see_throw.isChecked = localMap.tiles[posx][posy].canSeeThrow!!
        can_see_throw.setOnCheckedChangeListener { view, isChecked ->
            localMap.tiles[posx][posy].canSeeThrow = isChecked
        }

        listFiller.fillLayout(container, localMap.tiles[posx][posy].lookingForWayPrefix, context.getString(R.string.custom_looking_for_way_prefix))
        listFiller.fillLayout(container, localMap.tiles[posx][posy].thisTileCustomDescription, context.getString(R.string.custom_description))

        if (posx + 1 < localMap.tiles.size) {
            listFiller.fillLayout(container, localMap.tiles[posx + 1][posy].nextTileCustomDescription, context.getString(R.string.next_righ_tile_descr))
            listFiller.fillLayout(container, localMap.tiles[posx + 1][posy].behindCustomText, context.getString(R.string.next_righ_behind_text))
        }
        if (posx + 2 < localMap.tiles.size)
            listFiller.fillLayout(container, localMap.tiles[posx + 2][posy].customFarBehindText, context.getString(R.string.far_behind_righ_text))

        
        if (posx - 1 >= 0) {
            listFiller.fillLayout(container, localMap.tiles[posx - 1][posy].nextTileCustomDescription, context.getString(R.string.next_left_tile_descr))
            listFiller.fillLayout(container, localMap.tiles[posx - 1][posy].behindCustomText, context.getString(R.string.next_left_behind_text))
        }
        if (posx - 2 >= 0)
            listFiller.fillLayout(container, localMap.tiles[posx - 2][posy].customFarBehindText, context.getString(R.string.far_behind_left_text))


        if (posy - 1 >= 0) {
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 1].nextTileCustomDescription, context.getString(R.string.next_top_tile_descr))
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 1].behindCustomText, context.getString(R.string.next_top_behind_text))
        }
        if (posy - 2 >= 0)
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 2].customFarBehindText, context.getString(R.string.far_behind_top_text))


        if (posy + 1 < localMap.tiles[0].size) {
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 1].nextTileCustomDescription, context.getString(R.string.next_bottom_tile_descr))
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 1].behindCustomText, context.getString(R.string.next_bottom_behind_text))
        }
        if (posy + 2 < localMap.tiles[0].size)
            listFiller.fillLayout(container, localMap.tiles[posx][posy + 2].customFarBehindText, context.getString(R.string.far_behind_bottom_text))

        listFiller.addMapTileEvent(container, localMap, localMap.tiles[posx][posy], context.getString(R.string.events))
    }

    private fun mergeNulls(localMap: WorldMap, mapTile: MapTile) {
        if (localMap.events == null)
            localMap.events = ArrayList()
        if (mapTile.events == null)
            mapTile.events = ArrayList()
        if (mapTile.canSeeThrow == null)
            mapTile.canSeeThrow = true
    }
}