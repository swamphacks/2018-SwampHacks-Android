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
        db!!.dropTable("Events", true)
        db!!.dropTable("Sponsors", true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.createTable("Notification", true, "id" to INTEGER ,"title" to TEXT, "message" to TEXT , "type" to TEXT)
        db!!.createTable("Events", true, "id" to INTEGER ,
                "name" to TEXT, "description" to TEXT, "startTime" to TEXT+ NOT_NULL, "endTime" to TEXT+ NOT_NULL,
                "location" to TEXT, "numAttendees" to INTEGER, "type" to TEXT,"map" to TEXT,
                "avgRating" to TEXT, "day" to TEXT)
        db!!.createTable("Sponsors", true, "id" to INTEGER ,
                "name" to TEXT, "description" to TEXT, "logo" to TEXT, "link" to TEXT,
                "location" to TEXT, "tier" to TEXT)
        db!!.createTable("Reps", true, "sponsor" to TEXT ,
                "name" to TEXT, "image" to TEXT, "title" to TEXT)
    }

}