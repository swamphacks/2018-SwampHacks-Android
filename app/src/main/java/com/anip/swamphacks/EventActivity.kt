package com.anip.swamphacks

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.anip.swamphacks.helper.DatabaseHelper
import com.anip.swamphacks.model.SingleEvent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_activity.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by anip on 10/01/18.
 */
class EventActivity : AppCompatActivity(){
    lateinit var database: DatabaseHelper
    lateinit var qrScan : IntentIntegrator
    private var ref: DatabaseReference? = null
    private var refUpdate : DatabaseReference ?= null
    private var event : SingleEvent ?= null
    private var teamName  : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)
        teamName = getSharedPreferences("profile",0).getString("team","heron")
        Log.i("teamName",teamName)
        database = DatabaseHelper.Instance(applicationContext)
        var name = this.intent.getStringExtra("name")
        qrScan = IntentIntegrator(this)
        event = database.use {
            select("Events").columns("name", "description","startTime","endTime","location","type","day").whereArgs("(name = {name})","name" to name).limit(1).exec {
                parseSingle<SingleEvent>(classParser())
            }
        }

        val picasso : Picasso = Picasso.with(applicationContext)
//        picasso.load("https://firebasestorage.googleapis.com/v0/b/swamphacks-confirmed-attendees.appspot.com/o/events%2F_DSC0075.jpg?alt=media&token=beec0e54-a17b-4e26-ab8a-631bb206527d").into(image)
        supportActionBar!!.title = event!!.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val startTime = event!!.startTime?.toLong()
        val endTime = event!!.endTime?.toLong()
        val start = Date(startTime!!)
        val formatter = SimpleDateFormat("HH:mm a")
        val startTimeFormatted = formatter.format(startTime*1000)
        val endTimeFormatted = formatter.format(Date(endTime!!*1000))
        event_name.text = event!!.name
        location.text = event!!.location
        type.text = event!!.type
        var daynum : String
        if(event!!.day.equals("Friday")){
            daynum = "Day 1"
        }
        else if (event!!.day.equals("Saturday"))
        {
            daynum = "Day2"
        }
        else{
            daynum = "Day3"
        }
        day.text = event!!.day + " | " + daynum
        time.text = startTimeFormatted+ " : " + endTimeFormatted
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.qr_scanner -> {
                    qrScan.setOrientationLocked(false)
                    qrScan.initiateScan()
                }
            android.R.id.home -> {
                onBackPressed()
            }
            }


        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var intentResult : IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        println("Scanned qr code"+intentResult.contents)
        ref = FirebaseDatabase.getInstance().getReference("confirmed")

        val query = ref!!.orderByChild("email").equalTo(intentResult.contents)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    Log.i("hell", dataSnapshot.toString())
                   if(dataSnapshot.childrenCount.toInt() == 1){

                       val eventRef = FirebaseDatabase.getInstance().getReference("events").orderByChild("name").equalTo(event!!.name)
                       eventRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                           override fun onCancelled(p0: DatabaseError?) {
                               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                           }

                           override fun onDataChange(p0: DataSnapshot?) {
                               println(p0)
                               var eventId : String ? = null
                               p0!!.children.forEach{
                                   eventId = it.key

                               }
                               println(eventId)

                               val teamPath = "events/$eventId/attendants/people/$teamName"
                               val pointPath = "events/$eventId/attendants/points/$teamName"
                               val pathTotalPeople = "eventsData/people/$teamName"
                               val pathTotalPoints = "eventsData/people/$teamName"
                               val pointRef= FirebaseDatabase.getInstance().getReference(pointPath)
//                               pointRef.setValue(pointRef)
                               val teamRef = FirebaseDatabase.getInstance().getReference(teamPath)
                               val refTotalPeople = FirebaseDatabase.getInstance().getReference(pathTotalPeople)
                               val refTotalPoints = FirebaseDatabase.getInstance().getReference(pathTotalPoints)
                               teamRef.addListenerForSingleValueEvent(object : ValueEventListener{
                                   override fun onCancelled(p0: DatabaseError?) {
                                       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                   }

                                   override fun onDataChange(p0: DataSnapshot?) {
                                       print(p0)
                                       val count =  p0!!.value.toString().toInt()
                                       teamRef.setValue(count.inc())

                                       pointRef.addListenerForSingleValueEvent(object : ValueEventListener{
                                           override fun onCancelled(p0: DatabaseError?) {
                                               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                           }

                                           override fun onDataChange(p0: DataSnapshot?) {
                                               val points =  p0!!.value.toString().toInt()
                                               pointRef.setValue(points+10)

                                               refTotalPeople.addListenerForSingleValueEvent(object : ValueEventListener{
                                                   override fun onCancelled(p0: DatabaseError?) {
                                                       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                                   }

                                                   override fun onDataChange(p0: DataSnapshot?) {
                                                       val count =  p0!!.value.toString().toInt()
                                                       pointRef.setValue(count.inc())

                                                       refTotalPoints.addListenerForSingleValueEvent(object : ValueEventListener{
                                                           override fun onCancelled(p0: DatabaseError?) {
                                                               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                                           }

                                                           override fun onDataChange(p0: DataSnapshot?) {
                                                               val points =  p0!!.value.toString().toInt()
                                                               pointRef.setValue(points+10)
                                                           }

                                                       })
                                                   }

                                               })
                                           }

                                       })

                                       }
                               })
                           }
                       })
                       }
                       Log.i("hell","verified")
                   }
                    else{
                       Log.i("hell","Notverified")
                   }
                }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        super.onActivityResult(requestCode, resultCode, data)
    }
}