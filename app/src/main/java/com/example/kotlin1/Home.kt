package com.example.kotlin1

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.threetenabp.AndroidThreeTen

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.calendar_day_layout.view.*
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class Home : AppCompatActivity() {
    var isfragment=true;
    val manager=supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        changefrag2()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Calendar", Snackbar.LENGTH_LONG)
                //.setAction("Calendar", null).show()
                changefrag2()
        }

        timer.setOnClickListener {view ->
            Snackbar.make(view, "Timer", Snackbar.LENGTH_LONG)
                //.setAction("Timer", null).show()

            changefrag1()

        }
        reminder.setOnClickListener{view->
            Snackbar.make(view,"Reminders",Snackbar.LENGTH_LONG)
                //.setAction("Reminders",null).show()
                changefrag3()

        }

        map.setOnClickListener{view->
            val intent= Intent(this, MapsActivity::class.java)
            startActivity(intent)

        }

    }


    fun changefrag1(){
        val transaction=manager.beginTransaction()
        val fragment=Timer()
        transaction.replace(R.id.mylayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun changefrag2(){
        val transaction=manager.beginTransaction()
        val fragment=Calendar()
        transaction.replace(R.id.mylayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun changefrag3(){
        val transaction=manager.beginTransaction()
        val fragment=Reminders()
        transaction.replace(R.id.mylayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



}
