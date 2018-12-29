package com.bottools.botcontentfiller.ui.map_resources

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bottools.botcontentfiller.R

class ActivityEditMapResources  : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map)
        val fragment = MapResourcesListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        transaction.commit()
    }
}