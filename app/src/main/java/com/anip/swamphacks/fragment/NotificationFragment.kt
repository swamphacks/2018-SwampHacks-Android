package com.anip.swamphacks.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anip.swamphacks.R

/**
 * Created by anip on 11/11/17.
 */
class NotificationFragment : Fragment() {
    companion object {
        fun newInstance(): NotificationFragment {
            var fragmentHome = NotificationFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_notification, container, false)
        return rootView
    }
}