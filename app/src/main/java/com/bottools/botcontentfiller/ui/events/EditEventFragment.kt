package com.bottools.botcontentfiller.ui.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Event
import com.bottools.botcontentfiller.ui.views.layoutchildren.*
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditEventFragment : Fragment() {

    companion object {
        private const val EVENT_ID = "event_id"

        fun createInstance(eventId: Int): EditEventFragment {
            val fragment = EditEventFragment()
            val bundle = Bundle()
            bundle.putInt(EVENT_ID, eventId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var event: Event? = null
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
        activity?.setTitle(R.string.biome_editing)
    }

    private lateinit var element : EditPercentView
    private lateinit var element1 : EditPercentView
    private lateinit var element2 : EditPercentView
    private lateinit var element3 : EditBooleanView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val eventId = arguments?.getInt(EVENT_ID)
        if (eventId != null) {
            event = DatabaseManager.getById(eventId)
        }
        event?.let { event ->
            val views = ArrayList<AbstractChild>()
            views.add(EditStringArrayView(event.eventText, getString(R.string.edit_event_help), context))

            element = EditPercentView(event.probability, getString(R.string.probability), context)
            element1 = EditPercentView(event.probabilityFromAttentionFactor, getString(R.string.probability_from_attention_factor), context)
            element2 = EditPercentView(event.probabilityFromStealthFactor, getString(R.string.probability_from_stealth_factor), context)
            element3 = EditBooleanView(event.isGlobal, getString(R.string.is_global), context)
            views.add(element)
            views.add(element1)
            views.add(element2)
            views.add(element3)
            views.forEach {
                it.attachToView(container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        event?.probability = element.percent!!
        event?.probabilityFromAttentionFactor = element1.percent!!
        event?.probabilityFromStealthFactor = element2.percent!!
        event?.isGlobal = element3.boolean!!
        DatabaseManager.save(event!!)
    }

}
