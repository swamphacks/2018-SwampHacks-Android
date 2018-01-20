package com.anip.swamphacks

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.anip.swamphacks.fragment.*
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Event
import com.anip.swamphacks.model.Reps
import com.anip.swamphacks.model.Sponsor
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.db.*

class HomeActivity : AppCompatActivity() {
    private var content: FrameLayout? = null
    private lateinit var events : MutableList<Event>
    private lateinit var sponsors : MutableList<Sponsor>
    private lateinit var repsList : MutableList<Reps>


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
//                message.setText(R.string.title_home)
                val fragment = ProfileFragment.Companion.newInstance(this)
                addFragment(fragment, "Profile")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_events -> {
//                message.setText(R.string.title_dashboard)
                val fragment = EventsFragment.Companion.newInstance(this)
                addFragment(fragment, "Events")

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_announcements -> {
//                message.setText(R.string.title_notifications)
                val fragment = NotificationFragment.Companion.newInstance()
                addFragment(fragment, "Announcement")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_now -> {
//                message.setText(R.string.title_notifications)
                val fragment = NowFragment.Companion.newInstance(this)
                addFragment(fragment, "Happening Now")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sponsors -> {
//                message.setText(R.string.title_notifications)
                val fragment = SponsorFragment.Companion.newInstance(applicationContext)
                addFragment(fragment,"Sponsors")
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()

    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        
//        content = findViewById(R.id.content) as FrameLayout
        events = LoginActivity.events!!
        sponsors = LoginActivity.sponsors!!
        repsList = LoginActivity.reps!!
        Log.i("unique", LoginActivity.reps.size.toString())
        val database: DatabaseHelper = DatabaseHelper.Instance(applicationContext)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        database.use {
            delete("Events","1 ")
            delete("Sponsors", whereClause = "1")
            delete("Reps", whereClause = "1")
        }
//        database.use
        events.forEach {

            database.use {
                insert("Events", "id" to 12, "name" to
                        it.name, "description" to it.description!!, "day" to it.day, "startTime" to it.startTime.toString(), "endTime" to it.endTime.toString(),"type" to it.type,"location" to it.location)
//                println("StartTime"+it.startTime)
            }
        }
        println("Size  of events"+events.size)

        sponsors.forEach {
//            println("Sponsor Name" + it.tier)
            database.use {
                insert("Sponsors", "id" to 12, "name" to
                        it.name, "description" to it.description!!, "link" to it.link, "location" to it.location, "logo" to it.logoLink, "tier" to it.tier.toLowerCase())
            }

        }
        Log.i("hell_server_reps", repsList.size.toString())
        LoginActivity.reps.forEach{
            database.use{
                insert("Reps", "name" to it.name, "image" to it.image,"sponsor" to it.sponsor,"title" to it.title)
            }
        }



//        Log.i("hell  --->   ", LoginActivity.events.size.toString())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        if(intent.hasExtra("notification")){
            navigation.selectedItemId = R.id.navigation_announcements
        }
        else {
            val fragment = EventsFragment.Companion.newInstance(this)
            addFragment(fragment, "Events")
            navigation.selectedItemId = R.id.navigation_events
        }


    }
    private fun addFragment(fragment: Fragment, title : String) {
        supportActionBar!!.title =  title

        supportFragmentManager.popBackStack()
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content,fragment)
                .addToBackStack(null)
                .commit()
    }

}
