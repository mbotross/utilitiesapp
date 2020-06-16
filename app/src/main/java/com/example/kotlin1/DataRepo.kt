package com.example.kotlin1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataRepo (private val wordDao:DAO){
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    var dateimport = currentDateTime.format(formatter)

    val allWords: LiveData<List<Entries>> = wordDao.getEntries()
    var allevents:LiveData<List<Event>> = wordDao.getEvents()
    var time:String="0"
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

    suspend fun deletevent(event:Event){
        wordDao.deletevent(event)
    }
    fun settime(time:String){
        this.time=time
    }




}