package com.anip.swamphacks.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.EventAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Event
import com.anip.swamphacks.model.SingleEvent

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

/**
 * Created by anip on 06/01/18.
 */
class EventDayListFragment : Fragment(){
    var events: List<SingleEvent>? = null
    lateinit var adapter: EventAdapter

    companion object {
        fun newInstance(day: String): EventDayListFragment {
//            .events = eventList
            val gson = GsonBuilder().setPrettyPrinting().create()
//            val eventsString: String = gson.toJson(day)
            var fragmentHome = EventDayListFragment()
            var args = Bundle()
            args.putString("DAY", day)
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.i("hell","created")
        var rootView = inflater!!.inflate(R.layout.fragment_event_day, container, false)
//        rootView.setBackgroundColor(Color.WHITE)
        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        ref = FirebaseDatabase.getInstance().getReference("events")
        events = mutableListOf<SingleEvent>()
        val database: DatabaseHelper = DatabaseHelper.Instance(context)
        val day = arguments.get("DAY")
        database.use {
            events = select("Events").columns("name", "description","startTime","endTime","location","type","day").whereArgs("day = {day}", "day" to day).exec {
                parseList(classParser<SingleEvent>())
            }

        }

        println(events!!.size)


        adapter = EventAdapter(events!!,context)
        rv.adapter = adapter
//        ref.addValueEventListener(eventListener)

        return rootView
    }

}