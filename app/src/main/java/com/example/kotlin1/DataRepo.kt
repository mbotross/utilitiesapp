package com.example.kotlin1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData;

class DataRepo (private val wordDao:DAO){
    val allWords: LiveData<List<Entries>> = wordDao.getEntries()
    val allevents:LiveData<List<Event>> = wordDao.getEvents()
    suspend fun insert(word: Entries) {
        wordDao.insert(word)
    }
    suspend fun deletentry(word: Entries){
        wordDao.deletentry(word)
    }
    suspend fun insertevent(event:Event){
        wordDao.insertevent(event)
    }
}