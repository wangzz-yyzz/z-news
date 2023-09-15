package com.z_news.orm


import android.content.Context
import com.litesuits.orm.LiteOrm
import com.litesuits.orm.db.DataBaseConfig

class OrmDbHelper {
    private val DB_NAME = "ZNEWS"

    fun getInstance(context: Context): LiteOrm {
        val config = DataBaseConfig(context)
        config.dbName = DB_NAME
        config.debugged = true
        config.dbVersion = 1
        config.onUpdateListener = null

        return LiteOrm.newSingleInstance(config)
    }
}