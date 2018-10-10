package com.bottools.botcontentfiller.ui.biomes.biomtiles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.model.Building
import com.bottools.botcontentfiller.model.Event
import com.bottools.botcontentfiller.model.WorkType
import com.bottools.botcontentfiller.ui.views.layoutchildren.*
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditBiomeTileFragment : Fragment() {

    private var biomeTile : BiomeTile? = null
    private var events : ArrayList<Event>? = null
    private var buildings : ArrayList<Building>? = null
    private var workTypes : ArrayList<WorkType>? = null

    companion object {

        private const val TILE_ID ="tile_id"
        fun createInstance(biomeTileId: Int): EditBiomeTileFragment {
            val fragment = EditBiomeTileFragment()
            val bundle = Bundle()
            bundle.putInt(TILE_ID, biomeTileId)
            fragment.arguments = bundle
            return fragment
        }
    }
    private lateinit var thisTileCustomDescriptionView : EditSingleStringView
    private lateinit var nextTileCustomDescriptionView : EditSingleStringView
    private lateinit var customFarBehindTextView : EditSingleStringView
    private lateinit var isUnpassableView : EditBooleanView
    private lateinit var canSeeThrowView : EditBooleanView
    private lateinit var probabilityView : EditPercentView
    private lateinit var moveSpeedFactorView : EditPercentView
    private lateinit var stealthFactorView : EditPercentView
    private lateinit var addMoveSpeedView : EditIntView
    private lateinit var addStealthView : EditIntView
    private lateinit var element1 : MultiselectorFromList<Int>
    private lateinit var element2: MultiselectorFromList<Int>
    private lateinit var element3: MultiselectorFromList<Int>

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.biome_tile_editing)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val tileId = arguments?.getInt(TILE_ID)
        if (tileId != null) {
            biomeTile = DatabaseManager.getById(tileId)
            events = DatabaseManager.getList()
            buildings = DatabaseManager.getList()
            workTypes = DatabaseManager.getList()
        }

        biomeTile?.let { biomeTile ->
            val views = ArrayList<AbstractChild>()
            thisTileCustomDescriptionView = EditSingleStringView(biomeTile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context)
            nextTileCustomDescriptionView = EditSingleStringView(biomeTile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context)
            customFarBehindTextView = EditSingleStringView(biomeTile.customFarBehindText, context.getString(R.string.far_behind_descr), context)
            isUnpassableView = EditBooleanView(biomeTile.isUnpassable, context.getString(R.string.unpassable), context)
            canSeeThrowView = EditBooleanView(biomeTile.canSeeThrow, context.getString(R.string.can_see_throw), context)
            probabilityView = EditPercentView(biomeTile.probability, context.getString(R.string.probability), context)
            moveSpeedFactorView = EditPercentView(biomeTile.moveSpeedFactor, context.getString(R.string.speed_factor), context)
            stealthFactorView = EditPercentView(biomeTile.stealthFactor, context.getString(R.string.stealth_factor), context)
            addMoveSpeedView = EditIntView(biomeTile.additionalMoveSpeed, context.getString(R.string.speed_additional), context)
            addStealthView = EditIntView(biomeTile.additionalStealth, context.getString(R.string.stealth_additional), context)
            element1 = object : MultiselectorFromList<Int>(buildings!!.map { it.id }, biomeTile.initialBuildingsIds, getString(R.string.initial_buildings), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val result = BooleanArray(buildings!!.size)
                    buildings!!.forEachIndexed { i, it ->
                        if (biomeTile.initialBuildingsIds.contains(it.id)) {
                            result[i] = true
                        }
                    }
                    return result
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(buildings!!.map { it.name })
                }
            }
            element2 = object : MultiselectorFromList<Int>(events!!.map { it.id }, biomeTile.possibleEventsIds, getString(R.string.possible_events), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val result = BooleanArray(events!!.size)
                    events!!.forEachIndexed { i, it ->
                        if (biomeTile.possibleEventsIds.contains(it.id)) {
                            result[i] = true
                        }
                    }
                    return result
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(events!!.map { it.eventText[0]?: "" })
                }
            }
            element3 = object : MultiselectorFromList<Int>(workTypes!!.map { it.id }, biomeTile.availableWorkTypesIds, getString(R.string.available_worktypes), activity!!.supportFragmentManager, context) {
                override fun createCheckedMap(): BooleanArray {
                    val result = BooleanArray(workTypes!!.size)
                    workTypes!!.forEachIndexed { i, it ->
                        if (biomeTile.availableWorkTypesIds.contains(it.id)) {
                            result[i] = true
                        }
                    }
                    return result
                }

                override fun createItemsMap(): ArrayList<String> {
                    return ArrayList(workTypes!!.map { it.description })
                }
            }
            views.add(thisTileCustomDescriptionView)
            views.add(nextTileCustomDescriptionView)
            views.add(customFarBehindTextView)
            views.add(isUnpassableView)
            views.add(canSeeThrowView)
            views.add(probabilityView)
            views.add(moveSpeedFactorView)
            views.add(stealthFactorView)
            views.add(addMoveSpeedView)
            views.add(addStealthView)
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
        biomeTile?.let {
            with(it) {
                thisTileCustomDescription = thisTileCustomDescriptionView.str
                nextTileCustomDescription = nextTileCustomDescriptionView.str
                customFarBehindText = customFarBehindTextView.str
                isUnpassable = isUnpassableView.boolean
                canSeeThrow = canSeeThrowView.boolean
                probability = probabilityView.percent
                moveSpeedFactor = moveSpeedFactorView.percent
                stealthFactor = stealthFactorView.percent
                additionalMoveSpeed = addMoveSpeedView.value
                additionalStealth = addStealthView.value
            }
            DatabaseManager.save(it)
        }
    }
}