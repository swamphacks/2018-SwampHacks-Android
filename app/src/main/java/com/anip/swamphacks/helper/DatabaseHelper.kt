package com.anip.swamphacks.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by anip on 07/01/18.
 */
class  DatabaseHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx,"SwampHacks", null, 1){


    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun Instance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    db!!.dropTable("Notification", true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    db!!.createTable("Notification", true, "Id" to INTEGER ,"Title" to TEXT, "Message" to TEXT )
    }

}