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
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64


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
        val imageBytes = Base64.decode(repsList!![position]!!.image, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        vh.link.setImageBitmap(decodedImage)
        vh.message.text = repsList!![position].title
//        Picasso.with(context).load(decodedImage).transform(CircleTransform()).error(R.drawable.logo).into(vh!!.link)
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
        val message: TextView = view?.findViewById<TextView>(R.id.designation) as TextView

    }
}