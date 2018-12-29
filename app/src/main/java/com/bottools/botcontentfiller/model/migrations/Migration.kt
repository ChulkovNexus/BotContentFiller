package com.bottools.botcontentfiller.model.migrations

import io.realm.*
import io.realm.FieldAttribute
import io.realm.RealmObjectSchema




class Migration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if (oldVersion == 0L) {
            val biomeTileSchema = schema.get("BiomeTile")
            biomeTileSchema!!.addRealmListField("mapResources", Int::class.javaPrimitiveType)

            schema.create("MapResource")
                    .addField("id", String::class.java, FieldAttribute.PRIMARY_KEY)
                    .addField("name", String::class.java)
                    .addField("description", String::class.java)

            val mapTileSchema = schema.get("MapTile")
            mapTileSchema!!.addRealmListField("mapResources", Int::class.javaPrimitiveType)

//            var resources = RealmList<Int>()
//            var buildings = RealmList<Int>()
//            var main_skill = 0
//            var required_skill_level = 0
            val workTypeSchema = schema.get("WorkType")
            workTypeSchema!!.addRealmListField("resources", Int::class.javaPrimitiveType)
            workTypeSchema.addRealmListField("buildings", Int::class.javaPrimitiveType)
            workTypeSchema.addField("main_skill", Int::class.javaPrimitiveType)
            workTypeSchema.addField("required_skill_level", Int::class.javaPrimitiveType)

        }
    }

}
