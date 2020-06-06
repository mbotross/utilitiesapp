package com.example.kotlin1
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: DataRepo
    val allWords: LiveData<List<Entries>>



    init{
        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = DataRepo(wordsDao)
        allWords = repository.allWords
    }


    fun insert(word: Entries) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}