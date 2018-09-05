package com.bottools.botcontentfiller.ui.biomes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.ui.biomes.biomtiles.BiomeTilesListFragment
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.views.layoutchildren.AbstractChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.ButtonChild
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditSingleStringView
import com.bottools.botcontentfiller.ui.views.layoutchildren.EditStringArrayView
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditBiomeFragment : Fragment() {

    companion object {
        private const val BIOME_ID = "biome_id"

        fun createInstance(biomeId: Int): EditBiomeFragment {
            val fragment = EditBiomeFragment()
            val bundle = Bundle()
            bundle.putInt(BIOME_ID, biomeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var biome: Biome? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val biomeId = arguments?.getInt(BIOME_ID)
        if (biomeId != null) {
            biome = DatabaseManager.getBiome(biomeId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_map_defaults_fragment, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.biome_editing)
    }

    private lateinit var nameField : EditSingleStringView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!

        biome?.let { biome ->
            val views = ArrayList<AbstractChild>()
            views.add(ButtonChild(context.getString(R.string.edit_tiles), {
                openTilesFragment()
            }, context))
            nameField = EditSingleStringView(biome.name, context.getString(R.string.name), context)
            views.add(nameField)
            views.add(EditStringArrayView(biome.unpassabledefaults, context.getString(R.string.edit_unpassable_defaults), context))

            views.forEach {
                it.attachToView(container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        biome!!.name = nameField.str!!
        DatabaseManager.saveBiome(biome!!)
    }

    private fun openTilesFragment() {
        val biomeId = biome!!.id
        val fragment = BiomeTilesListFragment.createInstance(biomeId)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }

}