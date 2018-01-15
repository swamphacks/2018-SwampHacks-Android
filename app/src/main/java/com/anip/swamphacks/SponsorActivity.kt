package com.anip.swamphacks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Sponsor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sponsor.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

/**
 * Created by anip on 14/01/18.
 */
class SponsorActivity : AppCompatActivity() {
    lateinit var db : DatabaseHelper
    lateinit var sponsor : Sponsor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsor)
        db = DatabaseHelper.Instance(this)
        val name = intent.getStringExtra("name")
        db.use {
            sponsor = select("Sponsors").columns("name", "tier", "description", "link", "location", "logo").whereArgs("name = {name}", "name" to name).limit(1).exec {
                parseSingle(classParser())
            }
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = name
        Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/swamphacks-confirmed-attendees.appspot.com/o/logo.jpg?alt=media&token=6594fe12-7e4a-470d-8543-44852d074c29").into(logo)
        spName.text = sponsor.name
        Log.i("logo",sponsor.logo)
        location.text = sponsor.location
        about.text = sponsor.description


    }
}