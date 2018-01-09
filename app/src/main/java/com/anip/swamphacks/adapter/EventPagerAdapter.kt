package com.anip.swamphacks.adapter


import android.content.Context
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
//import android.support.Fragment

//import android.app.FragmentPager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import com.anip.swamphacks.fragment.EventDayListFragment
import com.anip.swamphacks.model.Event


/**
 * Created by anip on 06/01/18.
 */
class EventPagerAdapter(fm: FragmentManager, private val context: Context, private val titles : List<String>, eventsDayList : List<List<Event>>) : FragmentStatePagerAdapter(fm) {
    val events : List<List<Event>> = eventsDayList
    override fun getItem(position: Int): Fragment {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(position){
            0 -> return EventDayListFragment.newInstance(events[0])
            1 -> return EventDayListFragment.newInstance(events[1])
            2 -> return EventDayListFragment.newInstance(events[2])
        }

        return EventDayListFragment.newInstance(events[position])

    }

    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // return null to show no title.
        return titles[position]

    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
//        super.restoreState(state, loader)
    }
}