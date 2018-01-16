package com.anip.swamphacks.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.anip.swamphacks.R
import com.anip.swamphacks.model.SingleEvent

/**
 * Created by anip on 16/01/18.
 */
class NowEventsAdapter : BaseAdapter {

    private var eventList : List<SingleEvent>
    constructor(context: Context, eventsList: List<SingleEvent>) : super(){
        this.eventList = eventsList
//        this.context = context
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val vh: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.view_now_event, parent, false)
            vh = ViewHolder(view)
            view!!.tag = vh
            Log.i("JSA", "set Tag for ViewHolder, position: " + eventList.size)

        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }
        vh.name.text = eventList[position].name
//        vh.tvContent.text = notesList[position].content
        return view
    }

    override fun getItem(position: Int): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return eventList[position]
    }

    override fun getItemId(position: Int): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return eventList.size
    }
    private class ViewHolder(view: View?) {
        val name: TextView = view?.findViewById<TextView>(R.id.name) as TextView
//        val hos: TextView = view?.findViewById(R.id.tvContent) as TextView

    }
}