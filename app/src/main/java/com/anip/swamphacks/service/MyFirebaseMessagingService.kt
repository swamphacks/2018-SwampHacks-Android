package com.anip.swamphacks.service

import android.app.Notification
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.anip.swamphacks.R
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
        val notification = NotificationCompat.Builder(applicationContext)
        notification.setAutoCancel(true)
        notification.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(p0!!.data.getValue("title")).setContentText(p0!!.data.get("message"))
        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification.build())
//        database.use
        database.use {
            insert("Notification", "id" to p0!!.from, "title" to
                    p0!!.data.getValue("title"), "message" to p0.notification.body!!)
            var data = select("Notification").exec {
                parseList(classParser<Announcement>())
            }
            println("Size "+data.size)
        }

    }

}