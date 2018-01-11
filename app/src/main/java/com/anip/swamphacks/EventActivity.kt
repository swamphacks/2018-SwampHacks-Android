package com.anip.swamphacks

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.SingleEvent
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_activity.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

/**
 * Created by anip on 10/01/18.
 */
class EventActivity : AppCompatActivity(){
    lateinit var database: DatabaseHelper
    lateinit var qrScan : IntentIntegrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)
        database = DatabaseHelper.Instance(applicationContext)
        var name = this.intent.getStringExtra("name")
        qrScan = IntentIntegrator(this)
        var event = database.use {
            select("Events").columns("name", "description").whereArgs("(name = {name})","name" to name).limit(1).exec {
                parseSingle<SingleEvent>(classParser())
            }
        }
        scanner.setOnClickListener {
            qrScan.initiateScan()
        }
        val picasso : Picasso = Picasso.with(applicationContext)
        picasso.load("https://firebasestorage.googleapis.com/v0/b/swamphacks-confirmed-attendees.appspot.com/o/events%2F_DSC0075.jpg?alt=media&token=beec0e54-a17b-4e26-ab8a-631bb206527d").into(image)
        supportActionBar!!.title = event.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        description.text = event.description


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var intentResult : IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        println("Scanned qr code"+intentResult.contents)
        super.onActivityResult(requestCode, resultCode, data)
    }
}