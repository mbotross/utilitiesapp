package com.example.kotlin1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.reminders_fragment.*

class Reminders: Fragment() , onclickremind{

    private lateinit var recyclerView:RecyclerView
    private lateinit var viewmodel:RemViewModel
    private lateinit var adapter:WordListAdapter
    private val newWordActivityRequestCode = 1
    var ID:Int=2
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.reminders_fragment,container,false)

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context: Context = this.context ?: return
        recyclerView = view.findViewById(R.id.recyclerview)
        adapter = WordListAdapter(context,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?


        viewmodel= ViewModelProvider(requireActivity()).get(RemViewModel::class.java)
        viewmodel.allWords.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        addremind.setOnClickListener{
            val intent=Intent(this.context, NewReminder::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)








    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewReminder.EXTRA_REPLY)?.let {
                val word =Entries(ID,it)
                viewmodel.insert(word)
                ID++
            }
        }


    }

    override fun onitemclick(reminder: Entries, position: Int) {

        Toast.makeText(this.context,reminder.entry,Toast.LENGTH_SHORT).show()
        viewmodel.delete(reminder)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onStart() {
        super.onStart()
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