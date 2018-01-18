package com.anip.swamphacks.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.anip.swamphacks.R
import com.anip.swamphacks.model.Reps
import com.anip.swamphacks.util.CircleTransform
import com.squareup.picasso.Picasso

/**
 * Created by anip on 16/01/18.
 */
class RepresentativeAdapter : BaseAdapter{

    private var repsList : List<Reps>? = null
    private var context : Context

    constructor(context: Context, repsList: List<Reps>) : super(){
        this.repsList = repsList
        this.context = context
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val vh: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.view_reps, parent, false)
            vh = ViewHolder(view)
            view!!.tag = vh
            Log.i("JSA", "set Tag for ViewHolder, position: " + repsList!!.size)

        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }
        vh.name.text = repsList!![position].name
        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/swamphacks-applicants.appspot.com/o/DSC01265.jpg?alt=media&token=b6bf725c-e62e-4415-9238-8d151cad894be").transform(CircleTransform()).error(R.drawable.logo).into(vh!!.link)
//        vh.tvContent.text = notesList[position].content
        return view
    }

    override fun getItem(position: Int): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return repsList!![position]
    }

    override fun getItemId(position: Int): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return repsList!!.size
    }
    private class ViewHolder(view: View?) {
        val name: TextView = view?.findViewById<TextView>(R.id.name) as TextView
        val link : ImageView = view?.findViewById<ImageView>(R.id.profile) as ImageView
//        val hos: TextView = view?.findViewById(R.id.tvContent) as TextView

    }
}