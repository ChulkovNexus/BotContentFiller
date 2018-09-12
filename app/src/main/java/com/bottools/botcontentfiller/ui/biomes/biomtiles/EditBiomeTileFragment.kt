package com.bottools.botcontentfiller.ui.biomes.biomtiles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.ui.editmap.EditMapTileFragment
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditBooleanView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditPercentView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditBiomeTileFragment : Fragment() {

    private var biomeTile : BiomeTile? = null

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
        biomeTile = DatabaseManager.getBiomeTile(tileId)

        biomeTile?.let { biomeTile ->
            val views = ArrayList<AbstractChild>()
            thisTileCustomDescriptionView = EditSingleStringView(biomeTile.thisTileCustomDescription, context.getString(R.string.this_tile_descr), context)
            nextTileCustomDescriptionView = EditSingleStringView(biomeTile.nextTileCustomDescription, context.getString(R.string.next_tile_descr), context)
            customFarBehindTextView = EditSingleStringView(biomeTile.customFarBehindText, context.getString(R.string.far_behind_descr), context)
            isUnpassableView = EditBooleanView(biomeTile.isUnpassable, context.getString(R.string.unpassable), context)
            canSeeThrowView = EditBooleanView(biomeTile.canSeeThrow, context.getString(R.string.can_see_throw), context)
            probabilityView = EditPercentView(biomeTile.probability, context.getString(R.string.probability), context)

            views.add(thisTileCustomDescriptionView)
            views.add(nextTileCustomDescriptionView)
            views.add(customFarBehindTextView)
            views.add(isUnpassableView)
            views.add(canSeeThrowView)
            views.add(probabilityView)

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
            }
            DatabaseManager.saveBiomeTile(it)
        }
    }
}