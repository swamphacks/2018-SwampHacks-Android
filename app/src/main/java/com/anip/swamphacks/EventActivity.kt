package com.anip.swamphacks

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import android.R.string.cancel
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.anip.swamphacks.model.Team


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
    private var isEnded : Boolean ?= false
    var sharedPreference : SharedPreferences ?= null
    private var hacker : Team ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)
        invalidateOptionsMenu()

        teamName = getSharedPreferences("profile",0).getString("team","heron")
//        Log.i("teamName",teamName)
        database = DatabaseHelper.Instance(applicationContext)
//        supportActionBar.menu
        var name = this.intent.getStringExtra("name")
        qrScan = IntentIntegrator(this)
        event = database.use {
            select("Events").columns("name", "description","startTime","endTime","location","type","day").whereArgs("(name = {name})","name" to name).limit(1).exec {
                parseSingle<SingleEvent>(classParser())
            }
        }
        if(event!!.endTime!!.toLong() < System.currentTimeMillis()/1000){
            print("Entered if condition")
//            active_event.visibility = View.GONE
            isEnded = true
            event_end.visibility = View.VISIBLE
            val eventRef = FirebaseDatabase.getInstance().getReference("events").orderByChild("name").equalTo(event!!.name)
            eventRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    var eventId : String ?= null
                    p0!!.children.forEach{
                        eventId = it.key

                    }
                    getEventsData(eventId!!,false)
                }

            })

        }
        else{
            event_end.visibility = View.GONE
            active_event.visibility = View.VISIBLE
        }

//        picasso.load("https://firebasestorage.googleapis.com/v0/b/swamphacks-confirmed-attendees.appspot.com/o/events%2F_DSC0075.jpg?alt=media&token=beec0e54-a17b-4e26-ab8a-631bb206527d").into(image)
        supportActionBar!!.title = event!!.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val startTime = event!!.startTime?.toLong()
        val endTime = event!!.endTime?.toLong()
        val start = Date(startTime!!)
        val formatter = SimpleDateFormat("HH:mm a")
        val startTimeFormatted = formatter.format(Date(startTime*1000))
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
        time.text = startTimeFormatted+ " - " + endTimeFormatted
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        var sharedPreference =   getSharedPreferences("profile", Context.MODE_PRIVATE)

        menuInflater.inflate(R.menu.main,menu)
        menu!!.findItem(R.id.qr_scanner).isVisible = sharedPreference.getBoolean("isVolunteer",false)
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
        ref = FirebaseDatabase.getInstance().getReference("confirmed")

        val query = ref!!.orderByChild("email").equalTo(intentResult.contents)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                   if(dataSnapshot.childrenCount.toInt() == 1){
                       dataSnapshot.children.forEach{
//                           it.value.
                           var hacker : Team? = it.getValue(Team :: class.java)
                           teamName = hacker!!.team
                       }
                       val eventRef = FirebaseDatabase.getInstance().getReference("events").orderByChild("name").equalTo(event!!.name)
                       eventRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
                           override fun onCancelled(p0: DatabaseError?) {
                               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                           }

                           override fun onDataChange(p0: DataSnapshot?) {
                               var eventId : String ? = null
                               p0!!.children.forEach{
                                   eventId = it.key
                                   getEventsData(eventId!!,true)

                               }
                           }
                       })
                       }

//                       Log.i("hell","verified")
                    show_alert("\t\t\tSuccess !! User Verified")
                   }
                    else{
//                       Log.i("hell","Notverified")
                    show_alert("\t\tFailure : User Not Found")
                   }
                }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun getEventsData(eventId : String, update : Boolean){
        val gatorPath = "events/$eventId/attendants/points/gators"
        val heronPath = "events/$eventId/attendants/points/herons"
        val dragonPath = "events/$eventId/attendants/points/dragonflies"
        val turtlePath = "events/$eventId/attendants/points/turtles"

        if(update){
            val teamPath = "events/$eventId/attendants/people/$teamName"
            val pointPath = "events/$eventId/attendants/points/$teamName"
            val pathTotalPeople = "eventsData/people/$teamName"
            val pathTotalPoints = "eventsData/points/$teamName"
            val pointRef= FirebaseDatabase.getInstance().getReference(pointPath)
//                               pointRef.setValue(pointRef)
            val teamRef = FirebaseDatabase.getInstance().getReference(teamPath)
            val totalPointRef = FirebaseDatabase.getInstance().getReference(pathTotalPoints)
            val totalPeopleRef = FirebaseDatabase.getInstance().getReference(pathTotalPeople)
            teamRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    var value = p0!!.value.toString().toInt()
                    teamRef.setValue(value.inc())

                }

            })
            pointRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    var value = p0!!.value.toString().toInt()
                    pointRef.setValue(value+100)
                }

            })
            totalPointRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    var value = p0!!.value.toString().toInt()
                    totalPointRef.setValue(value+100)
                }

            })
            totalPeopleRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    var value = p0!!.value.toString().toInt()
                    totalPeopleRef.setValue(value.inc())
                }

            })


        }
        else{
            val gatorRef = FirebaseDatabase.getInstance().getReference(gatorPath)
            val heronRef = FirebaseDatabase.getInstance().getReference(heronPath)
            val turtleRef = FirebaseDatabase.getInstance().getReference(turtlePath)
            val dragonRef = FirebaseDatabase.getInstance().getReference(dragonPath)
            gatorRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    gators.text = "n.a."
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    gators.text = p0!!.value.toString()

                }

            })
            turtleRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    turtle.text = "n.a."
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    turtle.text = p0!!.value.toString()
                }

            })
            heronRef.addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    heron.text = "n.a."
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    heron.text = p0!!.value.toString()
                }

            })
            dragonRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    dragonfly.text = "n.a."
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    dragonfly.text = p0!!.value.toString()
                }

            })
        }


    }
    private fun show_alert(message : String){
        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage(message)
        builder1.setCancelable(true)

        builder1.setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11 = builder1.create()
        alert11.show()
    }


}