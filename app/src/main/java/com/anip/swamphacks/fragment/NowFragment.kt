package com.anip.swamphacks.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.NowEventsAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.SingleEvent
import kotlinx.android.synthetic.main.fragment_now.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

@SuppressLint("ValidFragment")
/**
 * Created by anip on 16/01/18.
 */
class NowFragment( passedContext : Context ) : Fragment() {
    lateinit var cont : Context
    lateinit var list : MutableList<SingleEvent>
    lateinit var eventList : List<SingleEvent>
    lateinit var adapter : NowEventsAdapter
    companion object {
        fun newInstance( context: Context) : NowFragment{
            var fragmentHome = NowFragment(context)
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_now, container, false)
        val database: DatabaseHelper = DatabaseHelper.Instance(context)
        val day = arguments.get("DAY")
        list = mutableListOf()
        database.use {
            eventList = select("Events").columns("name", "description", "startTime", "endTime").exec {
                parseList(classParser<SingleEvent>())
            }

        }
        val currentTime = 1516453400
        eventList.forEach {
            println("FRom db : "+it.startTime)
            if(it.startTime!!.toLong() < currentTime && it.endTime!!.toLong() > currentTime){
                list.add( it )
            }
        }
        Log.i("EventSize", eventList.size.toString())
        Log.i("Current Events", list.size.toString())
        adapter = NowEventsAdapter(context, list)
        rootView.findViewById<ListView>(R.id.events).adapter = adapter

        return rootView
    }
}