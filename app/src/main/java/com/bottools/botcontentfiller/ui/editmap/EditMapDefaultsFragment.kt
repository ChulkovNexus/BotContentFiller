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

    private lateinit var element :EditSingleStringView
    private lateinit var element1 :EditSingleStringView
    private lateinit var element2 :EditSingleStringView
    private lateinit var element3 :EditSingleStringView
    private lateinit var element4 :EditSingleStringView
    private lateinit var element5 :EditSingleStringView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val localMap = map!!
        val views = ArrayList<AbstractChild>()
        element = EditSingleStringView(localMap.defaultTopMovingTexts, context.getString(R.string.edit_top_defaults), context)
        element1 = EditSingleStringView(localMap.defaultBottomMovingTexts, context.getString(R.string.edit_bottom_defaults), context)
        element2 = EditSingleStringView(localMap.defaultRightMovingTexts, context.getString(R.string.edit_right_defaults), context)
        element3 = EditSingleStringView(localMap.defaultLeftMovingTexts, context.getString(R.string.edit_left_defaults), context)
        element4 = EditSingleStringView(localMap.defaultBehindsTexts, context.getString(R.string.edit_behind_defaults), context)
        element5 = EditSingleStringView(localMap.defaultUnpassableText, context.getString(R.string.edit_unpassable_defaults), context)
        views.add(element)
        views.add(element1)
        views.add(element2)
        views.add(element3)
        views.add(element4)
        views.add(element5)
        views.forEach {
            it.attachToView(container)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map!!.defaultTopMovingTexts = element.str!!
        map!!.defaultBottomMovingTexts = element1.str!!
        map!!.defaultRightMovingTexts = element2.str!!
        map!!.defaultLeftMovingTexts = element3.str!!
        map!!.defaultBehindsTexts = element4.str!!
        map!!.defaultUnpassableText = element5.str!!
    }
}