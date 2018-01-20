package com.anip.swamphacks.adapter

import android.content.Context
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import com.anip.swamphacks.fragment.SponsorTypeFragment
import android.support.v4.app.FragmentManager

/**
 * Created by anip on 13/01/18.
 */
class SponsorPagerAdapter(fm: FragmentManager, private val context: Context, private val titles: List<String>) : FragmentPagerAdapter(fm) {
//    val events : List<List<Event>> =
    override fun getItem(position: Int): Fragment {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(position){
            0 -> return SponsorTypeFragment.newInstance("heron")
            1 -> return SponsorTypeFragment.newInstance("turtle")
            2 -> return SponsorTypeFragment.newInstance("dragonfly")
        }

        return SponsorTypeFragment.newInstance("heron")

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
        super.restoreState(state, loader)
    }
}