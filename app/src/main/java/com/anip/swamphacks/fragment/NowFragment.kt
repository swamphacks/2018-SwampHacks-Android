package com.anip.swamphacks.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.anip.swamphacks.EventActivity
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
            eventList = select("Events").columns("name", "description","startTime","endTime","location","type","day").exec {
                parseList(classParser<SingleEvent>())
            }

        }
        val currentTime = System.currentTimeMillis()/1000
        eventList.forEach {
            if(it.startTime!!.toLong() < currentTime && it.endTime!!.toLong() > currentTime){
                list.add( it )
            }
        }
        adapter = NowEventsAdapter(context, list)
        var listView = rootView.findViewById<ListView>(R.id.events)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            val intent = Intent(context, EventActivity:: class.java )
            intent.putExtra("name",list[position].name)
            context.startActivity(intent)

        }
        return rootView
    }
}