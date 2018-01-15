package com.anip.swamphacks.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anip.swamphacks.EventActivity
import com.anip.swamphacks.R
import com.anip.swamphacks.model.Sponsor

/**
 * Created by anip on 13/01/18.
 */
class SponsorAdapter(val sponsors : List<Sponsor>, private val context: Context) : RecyclerView.Adapter<SponsorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.view_sponsor, parent, false)
        return SponsorAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return sponsors.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder?.txtName?.text = sponsors[position].name
        holder?.txtTitle?.text = sponsors[position].description
        println("Sponsor Name" + sponsors[position].name)
        holder?.itemView?.setOnClickListener {
            println("Clicked Item Name"+sponsors[position].name)
            val intent = Intent(context, EventActivity:: class.java )
            intent.putExtra("name",sponsors[position].name)
            context.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //        TODO Change Variable names
        val txtName = itemView.findViewById<TextView>(R.id.spName)
        val txtTitle = itemView.findViewById<TextView>(R.id.spTitle)
    }
}