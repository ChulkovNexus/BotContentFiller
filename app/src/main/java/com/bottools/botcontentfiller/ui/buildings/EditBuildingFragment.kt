package com.bottools.botcontentfiller.ui.buildings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Building
import com.bottools.botcontentfiller.model.WorkType
import com.bottools.botcontentfiller.ui.views.layoutchildren.*
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditBuildingFragment : Fragment() {

    companion object {
        private const val BUILDING_ID = "building_id"

        fun createInstance(eventId: Int): EditBuildingFragment {
            val fragment = EditBuildingFragment()
            val bundle = Bundle()
            bundle.putInt(BUILDING_ID, eventId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var building: Building? = null
    private var workTypes = ArrayList<WorkType>()
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
        activity?.setTitle(R.string.building_editing)
    }

    private lateinit var element : EditSingleStringView
    private lateinit var element1 : EditSingleStringView
    private lateinit var element2 : EditIntView
    private lateinit var element3 : EditIntView
    private lateinit var element4 : EditIntView
    private lateinit var element5 : MultiselectorFromList<Building>
    private lateinit var element6 : MultiselectorFromList<WorkType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val buildingId = arguments?.getInt(BUILDING_ID)
        if (buildingId != null) {
            building = DatabaseManager.getById(buildingId)
            workTypes = DatabaseManager.getList()
        }
        building?.let { building ->
            var requiredBuildings = DatabaseManager.getListByIds<Building>(building.requiredBuildingsIds.toTypedArray())
            val buildingsList = DatabaseManager.getList<Building>()
            buildingsList.filter { it.id != building.id }

            val views = ArrayList<AbstractChild>()
            element = EditSingleStringView(building.name, getString(R.string.name), context)
            element1 = EditSingleStringView(building.description, getString(R.string.description), context)
            element2 = EditIntView(building.energyOutput, getString(R.string.energy_output), context)
            element3 = EditIntView(building.energyRequered, getString(R.string.energy_requered), context)
            element4 = EditIntView(building.temperatureOtput, getString(R.string.temperature_output), context)
            element5 = object : MultiselectorFromList<Building>(buildingsList, requiredBuildings, getString(R.string.cant_work_without), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val booleanArray = BooleanArray(buildingsList.size)
                    buildingsList.forEachIndexed { i, it ->
                        booleanArray[i] = requiredBuildings.contains(it)
                    }
                    return booleanArray
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(buildingsList.map { it.name })
                }
            }
            element5 = object : MultiselectorFromList<Building>(buildingsList, requiredBuildings, getString(R.string.cant_work_without), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val booleanArray = BooleanArray(buildingsList.size)
                    buildingsList.forEachIndexed { i, it ->
                        booleanArray[i] = requiredBuildings.contains(it)
                    }
                    return booleanArray
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(buildingsList.map { it.name })
                }
            }
            element6 = object : MultiselectorFromList<WorkType>(workTypes, building.allowedWorkTypes, getString(R.string.cant_work_without), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val booleanArray = BooleanArray(workTypes.size)
                    workTypes.forEachIndexed { i, it ->
                        booleanArray[i] = building.allowedWorkTypes.contains(it)
                    }
                    return booleanArray
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(workTypes.map { it.description })
                }
            }
            element5.changeListener = object : AbstractChild.ChangeListener {
                override fun onChange() {
                    building.requiredBuildingsIds.clear()
                    building.requiredBuildingsIds.addAll(requiredBuildings.map { it.id })
                }
            }
            views.add(element)
            views.add(element1)
            views.add(element2)
            views.add(element3)
            views.add(element4)
            views.add(element5)
            views.add(element6)
            views.forEach {
                it.attachToView(container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        building?.name= element.str!!
        building?.description = element1.str!!
        building?.energyOutput = element2.value!!
        building?.energyRequered = element3.value!!
        building?.temperatureOtput = element4.value!!
        DatabaseManager.save(building!!)
    }

}
