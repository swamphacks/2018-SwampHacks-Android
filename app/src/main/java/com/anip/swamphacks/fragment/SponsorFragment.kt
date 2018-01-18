package com.anip.swamphacks.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anip.swamphacks.R
import com.anip.swamphacks.adapter.SponsorPagerAdapter

@SuppressLint("ValidFragment")
/**
 * Created by anip on 13/01/18.
 */
class SponsorFragment(passedContext: Context) : Fragment(){
    private var mSectionsPagerAdapter: SponsorPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    var cont = passedContext
    companion object {
        fun newInstance(context: Context): SponsorFragment {
            var fragmentHome = SponsorFragment(context)
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater!!.inflate(R.layout.fragment_sponsor, container, false)



        mSectionsPagerAdapter = SponsorPagerAdapter(childFragmentManager, cont, listOf("heron", "dragonfly", "turtle"))

        // Set up the ViewPager with the sections adapter.
        mViewPager = rootView.findViewById<ViewPager?>(R.id.container)
        mViewPager!!.setCurrentItem(0,true)
        mViewPager!!.offscreenPageLimit = 2
        mViewPager!!.setClipToPadding(false)
        mViewPager!!.setPadding(50, 0, 50, 0)
        val tabLayout = rootView.findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
        mViewPager!!.adapter = mSectionsPagerAdapter

        return rootView


    }
}