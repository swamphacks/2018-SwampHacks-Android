package com.anip.swamphacks

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import com.anip.swamphacks.fragment.EventsFragment
import com.anip.swamphacks.fragment.NotificationFragment
import com.anip.swamphacks.fragment.ProfileFragment
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Announcement
import com.anip.swamphacks.model.Event
import com.anip.swamphacks.model.SingleEvent
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class HomeActivity : AppCompatActivity() {
    private var content: FrameLayout? = null
    private lateinit var events : MutableList<Event>


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
//                message.setText(R.string.title_home)
                val fragment = ProfileFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_events -> {
//                message.setText(R.string.title_dashboard)
                val fragment = EventsFragment.Companion.newInstance(this)
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_announcements -> {
//                message.setText(R.string.title_notifications)
                val fragment = NotificationFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_now -> {
//                message.setText(R.string.title_notifications)
                val fragment = NotificationFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sponsors -> {
//                message.setText(R.string.title_notifications)
                val fragment = NotificationFragment.Companion.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        content = findViewById(R.id.content) as FrameLayout
        val gson = GsonBuilder().setPrettyPrinting().create()
        events = LoginActivity.events!!
        val database: DatabaseHelper = DatabaseHelper.Instance(applicationContext)
//        database.use
        events.forEach {

            database.use {
                insert("Events", "id" to 12, "name" to
                        it.name, "description" to it.description!!)
//                println("Size  of events"+data.size)
            }
        }

        Log.i("hell  --->   ", LoginActivity.events.size.toString())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = EventsFragment.Companion.newInstance(context = this)
        addFragment(fragment)


    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)

                .replace(R.id.content,fragment)
                .addToBackStack(null)
                .commit()
    }

}
