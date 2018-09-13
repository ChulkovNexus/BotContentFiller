package com.bottools.botcontentfiller.ui.worktype

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.WorkType
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.events.EditEventFragment
import com.bottools.botcontentfiller.utils.random

class WorkTypesListFragment: ListFragment() {

    private var workTypes = ArrayList<WorkType>()
    private lateinit var adapter: WorkTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.biomes_list)
        workTypes = DatabaseManager.getList()
        adapter = WorkTypesAdapter(activity!!, {
            openEventTilesListFragment(it)
        }, {
            removeEvent(it)
        })
        adapter.addAll(workTypes)
        listAdapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.plus_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.plus -> {
                addEvent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addEvent() {
        val workType = WorkType()
        workType.id = (0..Int.MAX_VALUE).random()
        DatabaseManager.save(workType)
        openEventTilesListFragment(workType)
    }

    private fun removeEvent(workType: WorkType) {
        workTypes.remove(workType)
        adapter.remove(workType)
        adapter.notifyDataSetChanged()
        DatabaseManager.remove<WorkType>(workType.id)
    }

    private fun openEventTilesListFragment(workType: WorkType) {
        val fragment = EditWorkTypeFragment.createInstance(workType.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}