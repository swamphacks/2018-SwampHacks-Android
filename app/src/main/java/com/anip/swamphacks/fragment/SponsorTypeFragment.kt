package com.anip.swamphacks.fragment

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
import com.anip.swamphacks.adapter.SponsorAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Sponsor
import com.google.gson.GsonBuilder
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

/**
 * Created by anip on 13/01/18.
 */

/**
 * Created by anip on 06/01/18.
 */
class SponsorTypeFragment : Fragment(){
    var sponsors: List<Sponsor>? = null
    lateinit var adapter: SponsorAdapter

    companion object {
        fun newInstance(type :  String): SponsorTypeFragment {
//            .events = eventLisT
            var fragmentHome = SponsorTypeFragment()
            var args = Bundle()
            args.putString("type", type)
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.i("hell","created")
        var rootView = inflater!!.inflate(R.layout.fragment_event_day, container, false)
        var type = arguments["type"]
        Log.i("hell", type.toString())
//        rootView.setBackgroundColor(Color.WHITE)
        val rv = rootView.findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//        ref = FirebaseDatabase.getInstance().getReference("events")
        sponsors = mutableListOf<Sponsor>()
        val database: DatabaseHelper = DatabaseHelper.Instance(context)
        database.use {
            sponsors = select("Sponsors").columns("name", "tier", "description", "link", "location", "logo").whereArgs("tier = {type}", "type" to type).exec {
                parseList(classParser<Sponsor>())
            }
        Log.i("hell","Sponsors Size for" + type + sponsors!!.size)

        }

//        events = gson.fromJson(this.arguments.getString("DAY"), object : TypeToken<List<Event>>() {}.type)


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


        println(sponsors!!.size)


        adapter = SponsorAdapter(sponsors!!,context)
        rv.adapter = adapter
//        ref.addValueEventListener(eventListener)

        return rootView
    }

}