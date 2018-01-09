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
import com.anip.swamphacks.adapter.EventAdapter
import com.anip.swamphacks.model.Event

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by anip on 06/01/18.
 */
class EventDayListFragment : Fragment(){
    var events: MutableList<Event>? = null
    lateinit var adapter: EventAdapter

    companion object {
        fun newInstance(eventList: List<Event>): EventDayListFragment {
//            .events = eventList
            val gson = GsonBuilder().setPrettyPrinting().create()
            val eventsString: String = gson.toJson(eventList)
            var fragmentHome = EventDayListFragment()
            var args = Bundle()
            args.putString("DAY", eventsString)
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        var rootView = inflater!!.inflate(R.layout.fragment_event_day, container, false)
        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        ref = FirebaseDatabase.getInstance().getReference("events")
        events = mutableListOf<Event>()
        events = gson.fromJson(this.arguments.getString("DAY"), object : TypeToken<List<Event>>() {}.type)


//        events.add(Event("Paul", "Mr"))
//        events.add(Event("Jane", "Miss"))
//        events.add(Event("John", "Dr"))
//        events.add(Event("Amy", "Mrs"))
//        val eventListener = object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                val children = p0!!.children
//
//                children.forEach {
//                    val event = it.getValue<Event>(Event::class.java)
//                    println(event!!.name)
//                    if(events!!.add(event)){
//                        println("error")
//                    }
//                    else{
//
//                    }
//
//                }
//                adapter.notifyDataSetChanged()
//
//
//
////                p0.children.mapNotNullTo(events) { it.getValue<Event>(Event::class.java) }
//            }
//        }


        println(events!!.size)


        adapter = EventAdapter(events!!)
        rv.adapter = adapter
//        ref.addValueEventListener(eventListener)

        return rootView
    }

}