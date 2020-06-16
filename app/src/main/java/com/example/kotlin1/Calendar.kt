package com.example.kotlin1

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.michaldrabik.classicmaterialtimepicker.CmtpDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime12PickedListener
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener
import java.util.Calendar.PM


class Calendar: Fragment(){
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
    private lateinit var allevents:ImageView
    private lateinit var eventname:EditText
    private lateinit var start_time:Button
    private lateinit var end_time:Button
    private lateinit var viewmodel:RemViewModel
    //private lateinit var searchview:SearchView
    //private lateinit var filterlist:RecyclerView
    private lateinit var adapter:SearchAdapter
    private lateinit var search:AutoCompleteTextView
    private lateinit var filter:Spinner
    private lateinit var searchimage:ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.calendar_fragment,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar=view.findViewById(R.id.calendarview)
//        scroll1=view.findViewById(R.id.switch1)
//        scroll2=view.findViewById(R.id.switch2)
        button=view.findViewById(R.id.createevent)
        allevents=view.findViewById(R.id.allevents)
        eventname=view.findViewById(R.id.eventname)
        start_time=view.findViewById(R.id.start_time)
        end_time=view.findViewById(R.id.end_time)
//        searchview=view.findViewById(R.id.searchView)
//        filterlist=view.findViewById(R.id.filterlist)
        search=view.findViewById(R.id.search)
        filter=view.findViewById(R.id.spinner)
        searchimage=view.findViewById(R.id.searchimage)

        val context: Context = this.context ?: return
        adapter = SearchAdapter(context)

//        filterlist.adapter = adapter
//        filterlist.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        //filterlist.alpha=0F
        viewmodel= ViewModelProvider(requireActivity()).get(RemViewModel::class.java)
        val events= ArrayList<String>()
        viewmodel.allEvents.observe(viewLifecycleOwner, Observer{ words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                events.clear()
                words.forEach{event->

                    events.add(event.event)


                }
            }

        })
        //search view

        val arrayadapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, events)
        val arrayadapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, events)

        search.setAdapter(arrayadapter)
        filter.adapter = arrayadapter2


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Managing changing between AM and PM
//        scroll1.setOnCheckedChangeListener { _, isChecked->
//            time1=scroll1.text.toString()
//
//            if(time1.equals("AM")){
//                scroll1.text="PM"
//                time1="PM"
//            } else{
//                scroll1.text="AM"
//                time1="AM"
//            }
//        }
//
//        scroll2.setOnCheckedChangeListener { _, isChecked->
//            time2=scroll2.text.toString()
//
//            if(time2.equals("AM")){
//                scroll2.text="PM"
//                time2="PM"
//            } else{
//                scroll2.text="AM"
//                time2="AM"
//            }
//        }


        //Inputs





    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        var starttime:String="5:30"
        var endtime:String="6:30"
        var zone1:String="PM"
        var zone2:String="PM"
        val t:Toast=Toast.makeText(activity?.applicationContext,"Please Make Sure all entries are filled",Toast.LENGTH_LONG)
        val t2:Toast=Toast.makeText(activity?.applicationContext,"Event Created",Toast.LENGTH_LONG)
        var formatted=viewmodel.getdate()

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            val msg = "Selected date is " + (month+1) + "/" + dayOfMonth + "/" + year
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            formatted= (month+1).toString() + "/" + dayOfMonth + "/" + year
            viewmodel.setnewdate(formatted)



        }
//        button.setOnClickListener {
//            if(eventname.text.toString().length>0){
//                event=eventname.text.toString()
//                println(event)
//            }
//            if(start_time.text.toString().length>0){
//                start=start_time.text.toString().toInt()
//            }
//            if(end_time.text.toString().length>0){
//                end=end_time.text.toString().toInt()
//            }
//            if(start>0 && end>0 && event.length>0){
//                //Event successfully created
//                //call view model to store event info
//                var eventcreated:Event= Event(eventname.text.toString(),starttime,endtime,formatted,
//                    scroll1.text as String, scroll2.text as String)
//                viewmodel.insertevent(eventcreated)
//                t2.show()
//            } else{
//                t.show()
//            }
//
//
//        }
        button.setOnClickListener{
            if(starttime.length>0 && endtime.length>0 && eventname.text.isNotEmpty()){
                val eventcreated:Event= Event(eventname.text.toString(),starttime,endtime,formatted,
                    zone1, zone2)
                viewmodel.insertevent(eventcreated)
                t2.show()
            }
            else{
                t.show()
            }
        }

        allevents.setOnClickListener{
            print("date"+formatted)
            val intent= Intent(this.context, AllEvents::class.java)
            intent.putExtra("date",formatted)
            intent.putExtra("search","")
            startActivity(intent)
        }
        searchimage.setOnClickListener{
            val intent=Intent(this.context,AllEvents::class.java)

            intent.putExtra("date",formatted)
            intent.putExtra("search",search.text.toString())
            println(search.text)
            startActivity(intent)
        }

        start_time.setOnClickListener{
            val timePicker = CmtpDialogFragment.newInstance()

            timePicker.show(childFragmentManager, "TimePickerTag")
            timePicker.setInitialTime12(5, 30,CmtpTime12.PmAm.PM)
            timePicker.setOnTime12PickedListener { time12 ->
                // Do something with picked time.
                starttime=time12.hour.toString() + ":" + time12.minute.toString()
                zone1=time12.pmAm.toString()
                start_time.setText(starttime.plus(" ").plus(zone1))
                println(starttime)

            }


        }


        end_time.setOnClickListener{
            val timePicker = CmtpDialogFragment.newInstance()
            timePicker.show(childFragmentManager, "TimePickerTag")
            timePicker.setInitialTime12(6, 30,CmtpTime12.PmAm.PM)
            timePicker.setOnTime12PickedListener { time12 ->
                // Do something with picked time.
                endtime=time12.hour.toString() + ":" + time12.minute.toString()
                zone2=time12.pmAm.toString()
                end_time.setText(endtime.plus(" ").plus(zone2))
                println(endtime)

            }

        }



//        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                filterlist.alpha= 1.0F
//                button.alpha=0F
//                getFilter().filter(query)
//                return false
//            }
//        })




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
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filteredlist= ArrayList<Event>()
//                var allevents:List<Event>?=viewmodel.allEvents.value
//                Handler(Looper.getMainLooper()).post {
//                viewmodel.allEvents.observe(viewLifecycleOwner, Observer { words ->
//                    // Update the cached copy of the words in the adapter.
//                    words?.let {
//
//                        allevents=words
//                        println(allevents)
//                    }
//                })}
//
//                if(constraint.toString().isEmpty()){
////                    if (allevents != null) {
////                        filteredlist.addAll(allevents!!)
////                    }
//                    filterlist.alpha=0F
//                    button.alpha=1F
//
//                }
//                else{
//                    allevents?.forEach { event ->
//                        if(event.event.toLowerCase().contains(constraint.toString().toLowerCase())){
//                            filteredlist.add(event)
//                            print(event.event)
//                        }
//                    }
//
//                }
//                val filterresults=FilterResults()
//                filterresults.values=filteredlist
//                return filterresults
//            }
//
//            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
//                val events = filterResults
//                adapter.clearEvents()
//                adapter.setEvents(events.values as? List<Event>)
//                adapter.notifyDataSetChanged()
//            }
//
//        }
//    }
//



}