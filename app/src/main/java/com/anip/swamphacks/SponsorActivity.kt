package com.anip.swamphacks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.anip.swamphacks.adapter.RepresentativeAdapter
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.Reps
import com.anip.swamphacks.model.Sponsor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sponsor.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

/**
 * Created by anip on 14/01/18.
 */
class SponsorActivity : AppCompatActivity() {
    lateinit var db : DatabaseHelper
    private var reps : RepresentativeAdapter? = null
    private var list : List<Reps>? = null
    lateinit var sponsor : Sponsor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsor)
        db = DatabaseHelper.Instance(this)
//        list = mutableListOf<Reps>()
        val name = intent.getStringExtra("name")
        db.use {
            sponsor = select("Sponsors").columns("name", "tier", "description", "link", "location", "logo").whereArgs("name = {name}", "name" to name).limit(1).exec {
                parseSingle(classParser())
            }
            list = select("Reps").columns("name", "image","title").whereArgs("sponsor = {sponsor}", "sponsor" to sponsor.name).parseList(classParser())
            println("reps size" + list)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = name
        Picasso.with(this).load(sponsor.logoLink).into(logoLink)
        spName.text = sponsor.name
//        Log.i("logo",sponsor.logoLink)
        location.text = sponsor.location
        about.text = sponsor.description
        reps = RepresentativeAdapter(this, list!!)
        peoples.adapter = reps



    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }


        return super.onOptionsItemSelected(item)

    }
}