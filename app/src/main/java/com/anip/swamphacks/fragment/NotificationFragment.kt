package com.anip.swamphacks.fragment

import android.app.Notification
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.AnnouncementAdapter
import com.anip.swamphacks.adapter.EventAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Announcement
import com.anip.swamphacks.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

/**
 * Created by anip on 11/11/17.
 */
class NotificationFragment : Fragment() {
    var notifications: MutableList<Announcement>? = null
    lateinit var adapter: AnnouncementAdapter
    companion object {
        fun newInstance(): NotificationFragment {
            var fragmentHome = NotificationFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_notification, container, false)
        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
        notifications = mutableListOf()
        rootView.setBackgroundColor(Color.WHITE)
        var anounceRef = FirebaseDatabase.getInstance().getReference("announcements")
        anounceRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                p0!!.children.forEach{
                    val notif = it.getValue(Announcement :: class.java)
                    notifications!!.add(notif!!)
                }
                rv.adapter.notifyDataSetChanged()
            }

        })
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        adapter = AnnouncementAdapter(notifications!!,context)
        rv.adapter = adapter

        return rootView
    }
}