package com.bottools.botcontentfiller.manager

import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.BiomeTile
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap
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

    fun loadMap(): WorldMap? {
        val realm = Realm.getDefaultInstance()
        val map = realm.where(WorldMap::class.java).findFirst()
        return if (map!=null) {
            val copyFromRealm = realm.copyFromRealm(map)
            val tiles = realm.where(MapTile::class.java).findAll()
            if (!tiles.isEmpty()) {
                copyFromRealm.fillFromBase(realm.copyFromRealm(tiles).toList())
            }
            copyFromRealm
        } else {
            null
        }
    }

    fun saveMap(map: WorldMap) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(map)
            val tiles = ArrayList<MapTile>()
            map.tiles.forEach {
                tiles.addAll(it)
            }
            realm.copyToRealmOrUpdate(tiles)
        }
    }

}