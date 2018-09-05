package com.bottools.botcontentfiller.manager

import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.BiomeTile
import io.realm.Realm

object DatabaseManager {

    fun getBiomes(): ArrayList<Biome> {
        val result = ArrayList<Biome> ()
        val realm = Realm.getDefaultInstance()
        val findAll = realm.where(Biome::class.java).findAll()
        result.addAll(realm.copyFromRealm(findAll))
        return result
    }

    fun removeBiome(biomeId: Int) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync  {
            val biomeInBase = it.where(Biome::class.java).equalTo("id", biomeId).findFirst()
            if (biomeInBase!=null) {
                biomeInBase.deleteFromRealm()
            }
        }
    }

    fun getBiome(biomeId: Int): Biome? {
        val realm = Realm.getDefaultInstance()
        val findFirst = realm.where(Biome::class.java).equalTo("id", biomeId).findFirst()
        return if (findFirst!=null) {
            realm.copyFromRealm(findFirst)
        } else {
            null
        }
    }

    fun saveBiome(biome: Biome) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(biome)
        }
    }

    fun saveBiomeTile(biomeTile: BiomeTile) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(biomeTile)
        }
    }

    fun getBiomeTile(tileId: Int?): BiomeTile? {
        val realm = Realm.getDefaultInstance()
        val findFirst = realm.where(BiomeTile::class.java).equalTo("id", tileId).findFirst()
        return if (findFirst!=null) {
            realm.copyFromRealm(findFirst)
        } else {
            null
        }
    }

    fun removeBiomeTile(biomeTile: BiomeTile) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync  {
            val biomeTileInBase = it.where(BiomeTile::class.java).equalTo("id", biomeTile.id).findFirst()
            if (biomeTileInBase!=null) {
                biomeTileInBase.deleteFromRealm()
            }
        }
    }

}