package com.bottools.botcontentfiller.ui.worktype

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.WorkType
import com.bottools.botcontentfiller.model.WorkTypeGroup
import com.bottools.botcontentfiller.ui.views.layoutchildren.*
import kotlinx.android.synthetic.main.edit_map_defaults_fragment.*

class EditWorkTypeFragment : Fragment() {

    companion object {
        private const val WORKTYPE_ID = "worktype_id"

        fun createInstance(worktypeId: Int): EditWorkTypeFragment {
            val fragment = EditWorkTypeFragment()
            val bundle = Bundle()
            bundle.putInt(WORKTYPE_ID, worktypeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var workType: WorkType? = null
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

    private lateinit var element : EditSingleStringView
    private lateinit var element1 : EditIntView
    private lateinit var element2 : EditPercentView
    private lateinit var element3 : StringListSelectorView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context!!
        val worktypeId = arguments?.getInt(WORKTYPE_ID)
        if (worktypeId != null) {
            workType = DatabaseManager.getById(worktypeId)
        }
        workType?.let { workType ->
            val views = ArrayList<AbstractChild>()
            var selectedPosition = WorkTypeGroup.values().indexOf(workType.getWorkTypeGroup())
            if (selectedPosition==-1) {
                selectedPosition = 0
            }
            element = EditSingleStringView(workType.description, getString(R.string.title_name), context)
            element1 = EditIntView(workType.baseWorkTime, getString(R.string.base_worktime), context)
            element2 = EditPercentView(workType.stealthFactor, getString(R.string.probability_from_stealth_factor), context)
            element3 = StringListSelectorView(WorkTypeGroup.values().map { it.name }.toList(), selectedPosition, getString(R.string.probability_from_stealth_factor), context)

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
        workType?.description = element.str!!
        workType?.baseWorkTime = element1.value!!
        workType?.stealthFactor = element2.percent!!
        workType?.setWorkTypeGroup(WorkTypeGroup.values()[element3.selectedPosition!!])
        DatabaseManager.save(workType!!)
    }

}
