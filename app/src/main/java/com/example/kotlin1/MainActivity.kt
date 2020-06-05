package com.example.kotlin1

import android.content.Intent

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonclk=startbutton
        val intent= Intent(this,Home::class.java)
        buttonclk.setOnClickListener(){
                startActivity(intent)

        }



    }
}
