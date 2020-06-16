package com.example.kotlin1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<SearchAdapter.WordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var events: List<Event>? = emptyList<Event>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun getItemCount(): Int{
        if(events!=null){
            return events!!.size
        }
        else{
            return 0
        }
    }

    internal fun setEvents(events: List<Event>?) {
        this.events = events
        notifyDataSetChanged()
    }


    fun geteventpos(position:Int): Event? {
        return events?.get(position)
    }
    fun clearEvents(){
        events= emptyList()
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = events?.get(position)
        if (current != null) {
            holder.name.text=current.event
        }

        if(itemCount==0){
            holder.name.text="No Events to display"
        }
    }

}