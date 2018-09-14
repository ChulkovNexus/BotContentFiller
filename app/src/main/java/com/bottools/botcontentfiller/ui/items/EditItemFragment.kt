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

class EditItemFragment : Fragment() {

    companion object {
        private const val ITEM_ID = "item_id"

        fun createInstance(eventId: Int): EditItemFragment {
            val fragment = EditItemFragment()
            val bundle = Bundle()
            bundle.putInt(ITEM_ID, eventId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var item: Item? = null
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
        activity?.setTitle(R.string.items_editing)
    }

    private lateinit var element : EditSingleStringView
    private lateinit var element1 : EditSingleStringView
    private lateinit var element2 : EditSingleStringView
    private lateinit var element3 : EditIntView
    private lateinit var element4 : EditIntView
    private lateinit var element5 : EditIntView
    private lateinit var element6 : EditIntView
    private lateinit var element7 : EditIntView
    private lateinit var element8 : EditIntView
    private lateinit var element9 : StringListSelectorView
    private lateinit var element10 : StringListSelectorView
    private lateinit var element11 : EditPercentView
    private lateinit var element12 : EditPercentView
    private lateinit var element13 : EditPercentView
    private lateinit var element14 : EditIntView
    private lateinit var element15 : MultiselectorFromList<BodyParts>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val itemId = arguments?.getInt(ITEM_ID)
        if (itemId != null) {
            item = DatabaseManager.getById(itemId)
        }
        item?.let { item ->

            var selectedItemGroupPosition = ItemGroup.values().indexOf(item.itemGroup)
            if (selectedItemGroupPosition==-1) {
                selectedItemGroupPosition = 0
            }
            var selectedWearTypePosition = WearType.values().indexOf(item.wearType)
            if (selectedWearTypePosition==-1) {
                selectedWearTypePosition = 0
            }

            val views = ArrayList<AbstractChild>()
            element = EditSingleStringView(item.name, getString(R.string.name), context)
            element1 = EditSingleStringView(item.descr, getString(R.string.description), context)
            element2 = EditSingleStringView(item.effects, getString(R.string.effects), context)
            element3 = EditIntView(item.weight, getString(R.string.weight), context)
            element4 = EditIntView(item.accuracy, getString(R.string.accuracy), context)
            element5 = EditIntView(item.range, getString(R.string.range), context)
            element6 = EditIntView(item.minDps, getString(R.string.minDps), context)
            element7 = EditIntView(item.maxDps, getString(R.string.maxDps), context)
            element8 = EditIntView(item.temperatureModificator, getString(R.string.temperature_modificator), context)
            element9 = StringListSelectorView(ItemGroup.values().map { it.name }.toList(), selectedItemGroupPosition, getString(R.string.item_group), context)
            element10 = StringListSelectorView(WearType.values().map { it.name }.toList(), selectedWearTypePosition, getString(R.string.wear_type), context)
            element11 = EditPercentView(item.accuracyRangeFactor, getString(R.string.accuracy_range_factor), context)
            element12 = EditPercentView(item.rangeDamageFactor, getString(R.string.accuracy_damage_factor), context)
            element13 = EditPercentView(item.armor, getString(R.string.armor), context)
            element14 = EditIntView(item.maxHealth, getString(R.string.max_helth), context)
            element15 = object : MultiselectorFromList<BodyParts>(BodyParts.values().toList(), item.bodyPartCoverage, getString(R.string.body_parts_coverage), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val booleanArray = BooleanArray(BodyParts.values().size)
                    BodyParts.values().forEachIndexed { i, it ->
                        booleanArray[i] = item.bodyPartCoverage.contains(it)
                    }
                    return booleanArray
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(BodyParts.values().map { it.name })
                }
            }

            views.add(element)
            views.add(element1)
            views.add(element2)
            views.add(element3)
            views.add(element4)
            views.add(element5)
            views.add(element6)
            views.add(element7)
            views.add(element8)
            views.add(element9)
            views.add(element10)
            views.add(element11)
            views.add(element12)
            views.add(element13)
            views.add(element14)
            views.forEach {
                it.attachToView(container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        item?.name = element.str!!
        item?.descr = element1.str!!
        item?.effects = element2.str!!
        item?.weight = element3.value!!
        item?.accuracy = element4.value!!
        item?.range = element5.value!!
        item?.minDps = element6.value!!
        item?.maxDps = element7.value!!
        item?.temperatureModificator = element8.value!!
        item?.maxHealth = element14.value!!
        item?.accuracyRangeFactor = element11.percent!!
        item?.rangeDamageFactor = element12.percent!!
        item?.armor = element13.percent!!
        item?.itemGroup = ItemGroup.values()[element9.selectedPosition!!]
        item?.wearType = WearType.values()[element10.selectedPosition!!]
        DatabaseManager.save(item!!)
    }

}
