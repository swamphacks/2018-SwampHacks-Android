package com.anip.swamphacks.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toolbar
import com.anip.swamphacks.HomeActivity
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.EventAdapter
import com.anip.swamphacks.adapter.EventPagerAdapter
import com.anip.swamphacks.model.Event
import com.google.firebase.database.*

/**
 * Created by anip on 11/11/17.
 */
@SuppressLint("ValidFragment")
class EventsFragment(passedContext : Context) : Fragment() {
    var cont = passedContext
    lateinit var ref : DatabaseReference
    private var mSectionsPagerAdapter: EventPagerAdapter? = null
    private var mViewPager: ViewPager? = null
//     var events : ArrayList<Event>? = ArrayList()
    var events: MutableList<Event>? = null
    var eventsTwo: MutableList<Event>? = null
    var eventsThree: MutableList<Event>? = null
    lateinit var adapter: EventAdapter

    companion object {
        fun newInstance(context: Context): EventsFragment {
            var fragmentHome = EventsFragment(context)
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_event, container, false)
        var eventLists : MutableList<MutableList<Event>> = mutableListOf<MutableList<Event>> ()
//        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
//        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        ref = FirebaseDatabase.getInstance().getReference("events")
        events = mutableListOf<Event>()
        eventsTwo = mutableListOf<Event>()
        eventsThree = mutableListOf<Event>()
//        eventLists = mutableListOf<List<Event>>()
        val event = Event()
//        events!!.add(event)
//        eventsTwo!!.add(event)
//        eventsThree!!.add(event)
        eventLists!!.add(events!!)
        eventLists!!.add(eventsTwo!!)
        eventLists!!.add(eventsThree!!)

//        events.add(Event("Paul", "Mr"))
//        events.add(Event("Jane", "Miss"))
//        events.add(Event("John", "Dr"))
//        events.add(Event("Amy", "Mrs"))
        val eventListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val children = p0.children

                children.forEach {
                    val event = it.getValue<Event>(Event::class.java)
                    println(event!!.name)
//                    if(events!!.add(event)){
//                        println("error")
//                    }
                    if(events!!.size<=5)
                    {
                        events!!.add(event)
                    }
                    else if (eventsTwo!!.size<=5){
                        eventsTwo!!.add(event)
                    }
                    else{
                        eventsThree!!.add(event)
                    }

                }

                eventLists!!.add(events!!)
                eventLists!!.add(eventsTwo!!)
                eventLists!!.add(eventsThree!!)

                mSectionsPagerAdapter!!.notifyDataSetChanged()
                val tabLayout = rootView.findViewById<View>(R.id.tabs) as TabLayout
                tabLayout.setupWithViewPager(mViewPager)
//                mSectionsPagerAdapter!!.destroyDrawingCache()
//                mViewPager!!.adapter = mSectionsPagerAdapter




//                p0.children.mapNotNullTo(events) { it.getValue<Event>(Event::class.java) }
            }
        }
//        val toolbar = rootView.findViewById<View>(R.id.toolbar) as Toolbar
//        cont.setSupportActionBar(toolbar)
        ref.addValueEventListener(eventListener)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = EventPagerAdapter(fragmentManager, cont, listOf("Friday", "Saturday", "Sunday"),eventLists)

        // Set up the ViewPager with the sections adapter.
        mViewPager = rootView.findViewById<ViewPager?>(R.id.container)
        mViewPager!!.adapter = mSectionsPagerAdapter



//        println(events!!.size)


//        adapter = PagerAdapter()
//        rv.adapter = adapter


        return rootView
    }

}