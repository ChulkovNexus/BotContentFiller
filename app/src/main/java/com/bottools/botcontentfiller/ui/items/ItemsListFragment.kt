package com.bottools.botcontentfiller.ui.items

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Event
import com.bottools.botcontentfiller.model.Item
import com.bottools.botcontentfiller.ui.Itemss.ItemsAdapter
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.ui.events.EditItemFragment
import com.bottools.botcontentfiller.utils.random

class ItemsListFragment: ListFragment() {

    private var items = ArrayList<Item>()
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.items_editing)
        items = DatabaseManager.getList()
        adapter = ItemsAdapter(activity!!, {
            openEventTilesListFragment(it)
        }, {
            removeEvent(it)
        })
        adapter.addAll(items)
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
        val item = Item()
        item.id = (0..Int.MAX_VALUE).random()
        DatabaseManager.save(item)
        openEventTilesListFragment(item)
    }

    private fun removeEvent(item: Item) {
        items.remove(item)
        adapter.remove(item)
        adapter.notifyDataSetChanged()
        DatabaseManager.remove<Event>(item.id)
    }

    private fun openEventTilesListFragment(item: Item) {
        val fragment = EditItemFragment.createInstance(item.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}
