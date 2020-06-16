package com.example.kotlin1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CalenAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CalenAdapter.WordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var events = emptyList<Event>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.namevent)
        val description:TextView=itemView.findViewById(R.id.list_description)
        val starttime:TextView=itemView.findViewById(R.id.start_time)
        val endtime:TextView=itemView.findViewById(R.id.end_time)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.event_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun getItemCount()= events.size

    internal fun setEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }


    fun geteventpos(position:Int):Event{
        return events[position]
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = events[position]
        holder.name.text=current.event
        holder.description.text=current.date
        holder.starttime.text="Start: ".plus(current.start).plus(' ' +current.time1)
        holder.endtime.text="End: ".plus(current.end).plus(' ' + current.time2)
        if(itemCount==0){
            holder.name.text="No Events to display"
        }
    }

}