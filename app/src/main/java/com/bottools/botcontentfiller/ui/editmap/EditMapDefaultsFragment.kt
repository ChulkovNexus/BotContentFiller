package com.bottools.botcontentfiller.ui.editmap

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditStringArrayView
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
        activity!!.title = getString(R.string.defaults_filling)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val localMap = map!!
        val views = ArrayList<AbstractChild>()
        val element = EditStringArrayView(localMap.defaultTopMovingTexts, context.getString(R.string.edit_top_defaults), context)
        val element1 = EditStringArrayView(localMap.defaultBottomMovingTexts, context.getString(R.string.edit_bottom_defaults), context)
        val element2 = EditStringArrayView(localMap.defaultRightMovingTexts, context.getString(R.string.edit_right_defaults), context)
        val element3 = EditStringArrayView(localMap.defaultLeftMovingTexts, context.getString(R.string.edit_left_defaults), context)
        val element4 = EditStringArrayView(localMap.defaultBehindsTexts, context.getString(R.string.edit_behind_defaults), context)
        val element5 = EditStringArrayView(localMap.defaultUnpassableText, context.getString(R.string.edit_unpassable_defaults), context)
        val element6 = EditStringArrayView(localMap.defaultLookingForWayPrefix, context.getString(R.string.edit_looking_for_way_defaults), context)
        val element7 = EditStringArrayView(localMap.beginningPhrases, context.getString(R.string.edit_beginning_phrases), context)
        views.add(element)
        views.add(element1)
        views.add(element2)
        views.add(element3)
        views.add(element4)
        views.add(element5)
        views.add(element6)
        views.add(element7)
        views.forEach {
            it.attachToView(container)
        }
    }
}