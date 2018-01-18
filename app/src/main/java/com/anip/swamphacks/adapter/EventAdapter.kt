package com.anip.swamphacks.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anip.swamphacks.AnnouncementActivity
import com.anip.swamphacks.EventActivity
import com.anip.swamphacks.R
import com.anip.swamphacks.model.Event
import com.anip.swamphacks.model.SingleEvent
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anip on 31/12/17.
 */
class EventAdapter(val eventList : List<SingleEvent>, private val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val startTime = eventList[position]!!.startTime?.toLong()
        val endTime = eventList[position]!!.endTime?.toLong()
        val start = Date(startTime!!)
        val formatter = SimpleDateFormat("HH:mm a")
        val startTimeFormatted = formatter.format(startTime*1000)
        val endTimeFormatted = formatter.format(Date(endTime!!*1000))
        holder?.txtName?.text = eventList[position].name
        holder?.txtTime?.text = startTimeFormatted + " - "+endTimeFormatted
        holder?.txtLoc?.text = "Location : "+eventList[position].location
        when(eventList[position].type){
            "Logistics" -> {
                holder?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorImp))
            }
            "Food" -> {
                holder?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorFood))
            }
            "Social" -> {
                holder?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorPurple))
            }
            "Techtalk" -> {
                holder?.bar!!.setBackgroundColor(context.resources.getColor(R.color.colorTechTalk))
            }
        }
        holder?.itemView?.setOnClickListener {
            println("Clicked Item Name"+eventList[position].name)
            val intent = Intent(context, EventActivity:: class.java )
            intent.putExtra("name",eventList[position].name)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.view_event, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return eventList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        TODO Change Variable names
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtTime = itemView.findViewById<TextView>(R.id.txtTime)
        val txtLoc = itemView.findViewById<TextView>(R.id.txtLoc)
        val bar = itemView.findViewById<View>(R.id.bar)
    }


}