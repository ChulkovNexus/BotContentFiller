package com.bottools.botcontentfiller.ui.edit_map

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditMapDefaultsFragment : Fragment() {

    var map: com.bottools.botcontentfiller.model.WorldMap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val ac = activity as ActivityEditMap
        map = ac.map
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        view.findViewById<View>(R.id.edit_unpassable_layout).visibility = View.GONE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val listFiller = LayoutFiller(context, activity!!.supportFragmentManager)
        val localMap = map!!
        listFiller.fillLayout(container, localMap.unpassableDefaults, context.getString(R.string.edit_unpassable_defaults))
        listFiller.fillLayout(container, localMap.defaultTopMovingTexts, context.getString(R.string.edit_top_defaults))
        listFiller.fillLayout(container, localMap.defaultBottomMovingTexts, context.getString(R.string.edit_bottom_defaults))
        listFiller.fillLayout(container, localMap.defaultRightMovingTexts, context.getString(R.string.edit_right_defaults))
        listFiller.fillLayout(container, localMap.defaultLookingForWayPrefix, context.getString(R.string.edit_looking_for_way_defaults))
        listFiller.fillLayout(container, localMap.defaultLeftMovingTexts, context.getString(R.string.edit_left_defaults))
        listFiller.fillLayout(container, localMap.defaultBehindsTexts, context.getString(R.string.edit_behind_defaults))
        listFiller.fillLayout(container, localMap.unknownsDefaults, context.getString(R.string.edit_unknown_defaults))
    }


}