package com.example.kotlin1

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.timer_fragment.*
import java.sql.Time
import java.util.*
import java.util.Timer

class Timer:Fragment(){

    var amnt:Long=0
    private lateinit var times:CountDownTimer
    var progresstat=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view= inflater.inflate(R.layout.timer_fragment,container,false)
        if(savedInstanceState==null){

        }
        else{
            amnt=savedInstanceState.getLong("amnt",0)
            entertime.setText(amnt.toString())
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if(savedInstanceState==null){
            amnt=0
        }
        else{
            amnt=savedInstanceState.getLong("amnt",0)
        }

        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)






    }

    override fun onStart() {
        super.onStart()

        val startbuttn = starttime
        val change = changetime
        var diff:Long =0
        var secondsInMilli:Long=0
        var minutesInMilli:Long =0
        var elapsedMinutes:Long=0
        var elapsedSeconds:Long=0
        var restore:Long=0
        var click = 0
        startbuttn.setOnClickListener {

            click=click+1
            if (entertime.text.toString().length > 0 && (click%2!=0)) {
                if(click==1){
                amnt = entertime.text.toString().toLong()*60*1000}
                times = object : CountDownTimer(amnt, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        diff = millisUntilFinished
                        restore=millisUntilFinished
                        secondsInMilli = 1000
                        minutesInMilli = secondsInMilli * 60
                        elapsedMinutes = diff / minutesInMilli
                        diff %= minutesInMilli
                        elapsedSeconds = diff / secondsInMilli
                        change.setText(elapsedMinutes.toString() + ":" + elapsedSeconds.toString())

                    }


                    override fun onFinish() {
                        change.setText("Done!")
                    }


                }.start()


                startbuttn.setText("Pause")

            }

            if(click%2==0){
                times.cancel()
                //val convertsec:Float= (elapsedSeconds/60).toFloat()
               // amnt=(((elapsedMinutes)*minutesInMilli)+(elapsedSeconds*secondsInMilli))/(60*1000).toFloat()
                amnt=restore
                diff = 0
                secondsInMilli= 0
                minutesInMilli = 0
                elapsedMinutes = 0
                elapsedSeconds= 0
                startbuttn.setText("Resume")


            }


        }
        val reset:Button=resest
        reset.setOnClickListener({
            click=0
            entertime.setText("")
            change.setText("")
            startbuttn.setText("Start")

        })

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putAll(outState)
        outState.putLong("amnt",amnt)
    }
}