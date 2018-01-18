package com.anip.swamphacks.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anip.swamphacks.AnnouncementActivity
import com.anip.swamphacks.R
import com.anip.swamphacks.model.Announcement

/**
 * Created by anip on 07/01/18.
 */
class AnnouncementAdapter(private val notifications : List<Announcement>, private val context : Context) : RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder?.txtTitle?.text = notifications[position].title
        holder?.txtMessage?.text = notifications[position].message
        when(notifications[position].type){
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
            val intent = Intent(context, AnnouncementActivity:: class.java )
//            intent.
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return notifications.size

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.view_notif, parent, false)
        return AnnouncementAdapter.ViewHolder(v);
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //        TODO Change Variable names
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)!!
        val txtMessage = itemView.findViewById<TextView>(R.id.txtMessage)!!
        val bar = itemView.findViewById<View>(R.id.bar)!!
    }
}