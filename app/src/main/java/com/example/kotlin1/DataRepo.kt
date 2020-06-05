package com.example.kotlin1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData;

class DataRepo (private val wordDao:DAO){
    val allWords: LiveData<List<Entries>> = wordDao.getEntries()
    suspend fun insert(word: Entries) {
        wordDao.insert(word)
    }
}