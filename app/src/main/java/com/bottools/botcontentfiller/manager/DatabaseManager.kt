package com.bottools.botcontentfiller.manager

import com.bottools.botcontentfiller.model.Biome
import io.realm.Realm

object DatabaseManager {

    fun getBiomes(): ArrayList<Biome> {
        val result = ArrayList<Biome> ()
        val realm = Realm.getDefaultInstance()
        val findAll = realm.where(Biome::class.java).findAll()
        result.addAll(realm.copyFromRealm(findAll))
        return result
    }

    fun saveBiomes(biome: Biome) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync  {
            it.insertOrUpdate(biome)
        }
    }
}