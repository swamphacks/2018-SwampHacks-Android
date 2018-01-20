package com.anip.swamphacks.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anip.swamphacks.LoginActivity
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.EventAdapter
import com.anip.swamphacks.adapter.EventPagerAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Event
import com.google.firebase.database.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert

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
//        rootView.setBackgroundColor(Color.WHITE)
        var eventLists : MutableList<MutableList<Event>> = mutableListOf<MutableList<Event>> ()
//        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
//        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        events = mutableListOf<Event>()
        ref = FirebaseDatabase.getInstance().getReference("events")

        val eventListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val children = p0.children

                children.forEach {
                    val event = it.getValue<Event>(Event::class.java)
                    events!!.add(event!!)

                }
                val database: DatabaseHelper = DatabaseHelper.Instance(cont)
                database.use {
                    delete("Events", "1 ")
                }

                   events!!.forEach {
                       database.use {
                           insert("Events", "id" to 12, "name" to
                                   it.name, "description" to it.description, "day" to it.day, "startTime" to it.startTime.toString(), "endTime" to it.endTime.toString(),"type" to it.type,"location" to it.location)
//                println("StartTime"+it.startTime)
                       }
                   }
                mViewPager!!.adapter = mSectionsPagerAdapter
                 mSectionsPagerAdapter!!.notifyDataSetChanged()

//                mSectionsPagerAdapter!!.notifyDataSetChanged()
//                val tabLayout = rootView.findViewById<View>(R.id.tabs) as TabLayout
//                tabLayout.setupWithViewPager(mViewPager)
//                mSectionsPagerAdapter!!.destroyDrawingCache()





//                p0.children.mapNotNullTo(events) { it.getValue<Event>(Event::class.java) }
            }
        }
//        val toolbar = rootView.findViewById<View>(R.id.toolbar) as Toolbar
//        cont.setSupportActionBar(toolbar)
//        ref.addValueEventListener(eventListener)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        if(cont.getSharedPreferences("profile",0).getBoolean("isFirst",true)){
//            Log.i("Executed ","Set Listner")
//            ref.addListenerForSingleValueEvent(eventListener)
//            var editor = cont.getSharedPreferences("profile",0).edit()
//            editor.putBoolean("isFirst",false)
//            editor.apply()
//        }

        mSectionsPagerAdapter = EventPagerAdapter(childFragmentManager, cont, listOf("Friday", "Saturday", "Sunday"),eventLists)
        mViewPager = rootView.findViewById<ViewPager?>(R.id.container)
        mViewPager!!.setCurrentItem(0,true)
        mViewPager!!.offscreenPageLimit = 5
        mViewPager!!.setClipToPadding(false)
        mViewPager!!.setPadding(50, 0, 50, 0)
        val tabLayout = rootView.findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
        mViewPager!!.adapter = mSectionsPagerAdapter

        return rootView
    }

}