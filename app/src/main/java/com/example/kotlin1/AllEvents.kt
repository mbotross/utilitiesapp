package com.example.kotlin1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllEvents : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:CalenAdapter
    private lateinit var viewmodel:RemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        recyclerView = findViewById(R.id.recyclerviewcal)
        adapter = CalenAdapter (this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        viewmodel= ViewModelProvider(this).get(RemViewModel::class.java)

        viewmodel.allEvents.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setEvents(it) }
        })

    }
}
