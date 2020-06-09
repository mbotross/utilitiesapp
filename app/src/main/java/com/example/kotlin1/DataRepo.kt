package com.example.kotlin1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class DataRepo (private val wordDao:DAO){
    var dateimport:String="6/7/2020"
    val allWords: LiveData<List<Entries>> = wordDao.getEntries()
    var allevents:LiveData<List<Event>> = wordDao.getEvents()
    suspend fun insert(word: Entries) {
        wordDao.insert(word)
    }
    suspend fun deletentry(word: Entries){
        wordDao.deletentry(word)
    }
    suspend fun insertevent(event:Event){
        wordDao.insertevent(event)
    }
     fun geteventdate(date:String): LiveData<List<Event>> {
        dateimport=date
        val list=wordDao.getEventdate(date)
        print(list.value)
         return list

    }




}