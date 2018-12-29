package com.bottools.botcontentfiller

import android.app.Application
import com.bottools.botcontentfiller.model.migrations.Migration
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("osome.realm")
                .schemaVersion(1)
                .migration(Migration())
                .build()
        Realm.setDefaultConfiguration(config)
    }

}