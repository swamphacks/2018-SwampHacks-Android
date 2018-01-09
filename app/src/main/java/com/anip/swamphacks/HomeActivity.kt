package com.anip.swamphacks

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.anip.swamphacks.fragment.EventsFragment
import com.anip.swamphacks.fragment.NotificationFragment
import com.anip.swamphacks.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var content: FrameLayout? = null

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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = ProfileFragment.Companion.newInstance()
        addFragment(fragment)


    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
//                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//                .remove(R.)
                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(null)
                .commit()
    }

}
