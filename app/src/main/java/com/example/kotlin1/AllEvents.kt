package com.example.kotlin1


import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class AllEvents : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:CalenAdapter
    private lateinit var viewmodel:RemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        val b = intent.extras
        val date = b.getString("date")
        val search:String? =b.getString("search")
        recyclerView = this.findViewById(R.id.recyclerviewcal)
        adapter = CalenAdapter (this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        viewmodel= ViewModelProvider(this).get(RemViewModel::class.java)
        val itemtouchhelpercallback=object:ItemTouchHelper.SimpleCallback(0, LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                actionState: Int, isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            this@AllEvents,
                            R.color.colorAccent)
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                viewmodel.deletevent(adapter.geteventpos(viewHolder.adapterPosition))
                adapter.notifyDataSetChanged()

            }

        }
        val itemtouchhelper= ItemTouchHelper(itemtouchhelpercallback)
        itemtouchhelper.attachToRecyclerView(recyclerView)


        if(search!=null && search.length>1){
            viewmodel.allEvents.observe(this,Observer{words->
                words?.let{
                    words.forEach{event->
                        if(event.event.equals(search)){
                            val temp=ArrayList<Event>()
                            temp.add(event)
                            adapter.setEvents(temp)
                        }

                    }
                }

            })

        }
        else {
            viewmodel.date.observe(this, Observer { words ->
                // Update the cached copy of the words in the adapter.
                println(words)

                words?.let {

                    adapter.setEvents(it)
                }
                if (words.isEmpty()) {
                    Toast.makeText(this, "No Events to display", Toast.LENGTH_LONG).show()
                }

            })
        }

        viewmodel.setdate(date)


    }
}
