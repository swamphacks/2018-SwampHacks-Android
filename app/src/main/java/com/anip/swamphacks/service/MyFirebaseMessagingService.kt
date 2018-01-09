package com.anip.swamphacks.service

import android.content.ContentValues.TAG
import android.util.Log
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Announcement
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

/**
 * Created by anip on 07/01/18.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
//        super.onMessageReceived(p0)
        Log.d(TAG, "From: " + p0!!.from)
        Log.d(TAG, "Notification Message Body: " + p0.notification.body!!)
        Log.d(TAG, "Notification Message Body: " + p0.data.getValue("title"))
        val database: DatabaseHelper = DatabaseHelper.Instance(applicationContext)
//        database.use
        database.use {
            insert("Notification", "Id" to p0!!.from, "Title" to
                    p0!!.data.getValue("title"), "Message" to p0.notification.body!!)
            var data = select("Notification").exec {
                parseList(classParser<Announcement>())
            }
            println("Size "+data.size)
        }

    }

}