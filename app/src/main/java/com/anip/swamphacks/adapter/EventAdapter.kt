package com.anip.swamphacks.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anip.swamphacks.R
import com.anip.swamphacks.model.Event

/**
 * Created by anip on 31/12/17.
 */
class EventAdapter(val eventList : List<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder?.txtName?.text = eventList[position].name
        holder?.txtTitle?.text = eventList[position].description
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.view_event, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return eventList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        TODO Change Variable names
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
    }


}