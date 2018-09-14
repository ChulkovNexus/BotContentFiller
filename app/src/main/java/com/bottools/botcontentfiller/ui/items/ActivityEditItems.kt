package com.bottools.botcontentfiller.ui.events

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.ui.items.ItemsListFragment


class ActivityEditItems : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map)
        val fragment = ItemsListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        transaction.commit()
    }
}