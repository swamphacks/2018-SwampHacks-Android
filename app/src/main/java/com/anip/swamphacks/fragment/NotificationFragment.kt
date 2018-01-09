package com.anip.swamphacks.fragment

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
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

/**
 * Created by anip on 11/11/17.
 */
class NotificationFragment : Fragment() {
    var notifications: List<Announcement>? = null
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
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        notifications = mutableListOf()
        val database: DatabaseHelper = DatabaseHelper.Instance(context)
        notifications = database.use {
            select("Notification").exec {
                parseList(classParser())
            }
        }
        adapter = AnnouncementAdapter(notifications!!,context)
        rv.adapter = adapter

        return rootView
    }
}