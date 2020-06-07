package com.example.kotlin1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter internal constructor(
    context: Context, var clicklistener:onclickremind
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Entries>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
        fun initialize(item:Entries,action:onclickremind){
            val id=item.id
            val entry=item.entry
            wordItemView.text=entry
            itemView.setOnClickListener{
                action.onitemclick(item,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text=current.entry
       // holder.wordItemView.text = current.id.toString().plus(".  ").plus(current.entry)
        holder.initialize(current,clicklistener)

    }

    internal fun setWords(words: List<Entries>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}


interface onclickremind{
    fun onitemclick(reminder:Entries,position:Int)


}