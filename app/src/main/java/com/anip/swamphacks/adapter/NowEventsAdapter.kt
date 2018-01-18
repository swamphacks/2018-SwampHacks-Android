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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anip on 16/01/18.
 */
class NowEventsAdapter : BaseAdapter {

    private var eventList : List<SingleEvent>
    private var context : Context
    constructor(context: Context, eventsList: List<SingleEvent>) : super(){
        this.eventList = eventsList
        this.context = context
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
//        vh.name.text = eventList[position].name
        val startTime = eventList[position]!!.startTime?.toLong()
        val endTime = eventList[position]!!.endTime?.toLong()
        val start = Date(startTime!!)
        val formatter = SimpleDateFormat("HH:mm a")
        val startTimeFormatted = formatter.format(startTime*1000)
        val endTimeFormatted = formatter.format(Date(endTime!!*1000))
        vh?.txtName?.text = eventList[position].name
        vh?.txtTime?.text = startTimeFormatted + " - "+endTimeFormatted
        vh?.txtLoc?.text = "Location : "+eventList[position].location
        when(eventList[position].type){
            "Logistics" -> {
                vh?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorImp))
            }
            "Food" -> {
                vh?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorFood))
            }
            "Social" -> {
                vh?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorPurple))
            }
            "Techtalk" -> {
                vh?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorTechTalk))
            }
        }
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
        val txtName = view!!.findViewById<TextView>(R.id.txtName)
        val txtTime = view!!.findViewById<TextView>(R.id.txtTime)
        val txtLoc = view!!.findViewById<TextView>(R.id.txtLoc)
        val bar = view!!.findViewById<View>(R.id.bar)
//        val hos: TextView = view?.findViewById(R.id.tvContent) as TextView

    }
}