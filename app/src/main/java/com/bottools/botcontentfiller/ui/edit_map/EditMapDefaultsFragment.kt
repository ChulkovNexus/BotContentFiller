package com.bottools.botcontentfiller.ui.edit_map

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
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
        val localMap = map!!
        val views = ArrayList<AbstractChild>()
        views.add(EditSingleStringView(localMap.defaultTopMovingTexts, context.getString(R.string.edit_top_defaults), context))
        views.add(EditSingleStringView(localMap.defaultBottomMovingTexts, context.getString(R.string.edit_bottom_defaults), context))
        views.add(EditSingleStringView(localMap.defaultRightMovingTexts, context.getString(R.string.edit_right_defaults), context))
        views.add(EditSingleStringView(localMap.defaultLeftMovingTexts, context.getString(R.string.edit_left_defaults), context))
        views.add(EditSingleStringView(localMap.defaultBehindsTexts, context.getString(R.string.edit_behind_defaults), context))
        views.forEach {
            it.attachToView(container)
        }
    }
}