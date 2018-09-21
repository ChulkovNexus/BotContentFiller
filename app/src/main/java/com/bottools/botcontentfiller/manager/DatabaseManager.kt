package com.bottools.botcontentfiller.manager

import com.bottools.botcontentfiller.model.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject

object DatabaseManager {
//
//    fun getBiomes(): ArrayList<Biome> {
//        val result = ArrayList<Biome> ()
//        val realm = Realm.getDefaultInstance()
//        val findAll = realm.where(Biome::class.java).findAll()
//        result.addAll(realm.copyFromRealm(findAll))
//        return result
//    }
//
//    fun removeBiome(biomeId: Int) {
//        val realm = Realm.getDefaultInstance()
//        realm.executeTransactionAsync  {
//            val biomeInBase = it.where(Biome::class.java).equalTo("id", biomeId).findFirst()
//            if (biomeInBase!=null) {
//                biomeInBase.deleteFromRealm()
//            }
//        }
//    }
//
//    fun getBiome(biomeId: Int): Biome? {
//        val realm = Realm.getDefaultInstance()
//        val findFirst = realm.where(Biome::class.java).equalTo("id", biomeId).findFirst()
//        return if (findFirst!=null) {
//            realm.copyFromRealm(findFirst)
//        } else {
//            null
//        }
//    }
//
//    fun saveBiome(biome: Biome) {
//        val realm = Realm.getDefaultInstance()
//        realm.executeTransaction {
//            it.copyToRealmOrUpdate(biome)
//        }
//    }

//    fun saveBiomeTile(biomeTile: BiomeTile) {
//        val realm = Realm.getDefaultInstance()
//        realm.executeTransaction {
//            it.copyToRealmOrUpdate(biomeTile)
//        }
//    }
//
//    fun getBiomeTile(tileId: Int?): BiomeTile? {
//        val realm = Realm.getDefaultInstance()
//        val findFirst = realm.where(BiomeTile::class.java).equalTo("id", tileId).findFirst()
//        return if (findFirst!=null) {
//            realm.copyFromRealm(findFirst)
//        } else {
//            null
//        }
//    }

//    fun removeBiomeTile(biomeTile: BiomeTile) {
//        val realm = Realm.getDefaultInstance()
//        realm.executeTransactionAsync  {
//            val biomeTileInBase = it.where(BiomeTile::class.java).equalTo("id", biomeTile.id).findFirst()
//            if (biomeTileInBase!=null) {
//                biomeTileInBase.deleteFromRealm()
//            }
//        }
//    }

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

    inline fun <reified T : RealmObject> getList(): ArrayList<T>{
        val result = ArrayList<T> ()
        val realm = Realm.getDefaultInstance()
        val findAll = realm.where(T::class.java).findAll()
        result.addAll(realm.copyFromRealm(findAll))
        return result
    }

    fun <T : RealmObject>save(event: T) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(event)
        }
    }

    inline fun <reified T:  RealmObject>remove(id: Int) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync  {
            val biomeInBase = it.where(T::class.java).equalTo("id", id).findFirst()
            if (biomeInBase!=null) {
                biomeInBase.deleteFromRealm()
            }
        }
    }

    inline fun <reified T:  RealmObject>getById(id: Int): T? {
        val realm = Realm.getDefaultInstance()
        val findFirst = realm.where(T::class.java).equalTo("id", id).findFirst()
        return if (findFirst!=null) {
            realm.copyFromRealm(findFirst)
        } else {
            null
        }
    }

    inline fun <reified T:  RealmObject>getListByIds(requiredBuildingsIds: Array<Int>): ArrayList<T> {
        val result = ArrayList<T> ()
        val realm = Realm.getDefaultInstance()
        val findAll = realm.where(T::class.java).`in`("id", requiredBuildingsIds).findAll()
        result.addAll(realm.copyFromRealm(findAll))
        return result
    }
}