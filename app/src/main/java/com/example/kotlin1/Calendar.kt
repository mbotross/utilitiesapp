package com.example.kotlin1

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.reminders_fragment.*
import kotlinx.android.synthetic.main.timer_fragment.*
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class Calendar: Fragment() {
    var start:Int=0
    var end:Int=0
    var event:String=""
    var time1:String="AM"
    var time2:String="AM"
    var Date:String=""
    private lateinit var calendar:CalendarView
    private lateinit var scroll1:Switch
    private lateinit var scroll2:Switch
    private lateinit var button:Button
    private lateinit var allevents:Button
    private lateinit var eventname:EditText
    private lateinit var start_time:EditText
    private lateinit var end_time:EditText
    private lateinit var viewmodel:RemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.calendar_fragment,container,false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar=view.findViewById(R.id.calendarview)
        scroll1=view.findViewById(R.id.switch1)
        scroll2=view.findViewById(R.id.switch2)
        button=view.findViewById(R.id.createevent)
        allevents=view.findViewById(R.id.allevents)
        eventname=view.findViewById(R.id.eventname)
        start_time=view.findViewById(R.id.start_time)
        end_time=view.findViewById(R.id.end_time)



        viewmodel= ViewModelProvider(requireActivity()).get(RemViewModel::class.java)




    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Managing changing between AM and PM
        scroll1.setOnCheckedChangeListener { _, isChecked->
            time1=scroll1.text.toString()

            if(time1.equals("AM")){
                scroll1.text="PM"
                time1="PM"
            } else{
                scroll1.text="AM"
                time1="AM"
            }
        }

        scroll2.setOnCheckedChangeListener { _, isChecked->
            time2=scroll2.text.toString()

            if(time2.equals("AM")){
                scroll2.text="PM"
                time2="PM"
            } else{
                scroll2.text="AM"
                time2="AM"
            }
        }


        //Inputs





    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val t:Toast=Toast.makeText(activity?.applicationContext,"Please Make Sure all entries are filled",Toast.LENGTH_LONG)
        val t2:Toast=Toast.makeText(activity?.applicationContext,"Event Created",Toast.LENGTH_LONG)

        button.setOnClickListener {
            if(eventname.text.toString().length>0){
                event=eventname.text.toString()
                println(event)
            }
            if(start_time.text.toString().length>0){
                start=start_time.text.toString().toInt()
            }
            if(end_time.text.toString().length>0){
                end=end_time.text.toString().toInt()
            }
            if(start>0 && end>0 && event.length>0){
                //Event successfully created
                //call view model to store event info

                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
                var formatted = currentDateTime.format(formatter)
                var eventcreated:Event= Event(eventname.text.toString(),start,end,formatted)
                calendar?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
                    val msg = "Selected date is " + (month+1) + "/" + dayOfMonth + "/" + year
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                     formatted= (month+1).toString() + "/" + dayOfMonth + "/" + year
                     eventcreated.date=formatted


                }

                viewmodel.insertevent(eventcreated)
                //t2.show()
            } else{
                t.show()
            }


        }
        allevents.setOnClickListener{
            print(viewmodel.allEvents)
            val intent= Intent(this.context, AllEvents::class.java)
            startActivity(intent)
        }




    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()

    }


}