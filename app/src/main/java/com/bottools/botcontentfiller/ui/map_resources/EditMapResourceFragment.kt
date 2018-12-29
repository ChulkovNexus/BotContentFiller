package com.bottools.botcontentfiller.ui.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.*
import com.bottools.botcontentfiller.ui.views.layoutchildren.*
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditMapResourceFragment : Fragment() {

    companion object {
        private const val ITEM_ID = "item_id"

        fun createInstance(eventId: Int): EditMapResourceFragment {
            val fragment = EditMapResourceFragment()
            val bundle = Bundle()
            bundle.putInt(ITEM_ID, eventId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var item: MapResource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.resources_editing)
    }

    private lateinit var element : EditSingleStringView
    private lateinit var element1 : EditSingleStringView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val itemId = arguments?.getInt(ITEM_ID)
        if (itemId != null) {
            item = DatabaseManager.getById(itemId)
        }
        item?.let { item ->
            val views = ArrayList<AbstractChild>()
            element = EditSingleStringView(item.name, getString(R.string.name), context)
            element1 = EditSingleStringView(item.description, getString(R.string.description), context)

            views.add(element)
            views.add(element1)
            views.forEach {
                it.attachToView(container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        item?.name = element.str!!
        item?.description = element1.str!!
        DatabaseManager.save(item!!)
    }

}
