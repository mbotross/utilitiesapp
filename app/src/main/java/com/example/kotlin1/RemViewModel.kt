package com.example.kotlin1
import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RemViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: DataRepo
    val allWords: LiveData<List<Entries>>
    val allEvents: LiveData<List<Event>>
    private val __date:MutableLiveData<String>



    init{
        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = DataRepo(wordsDao)
        allWords = repository.allWords
        allEvents=repository.allevents

        __date = MutableLiveData()


    }



    val date: LiveData<List<Event>> = Transformations
        .switchMap(__date){change->
            println("called")
            repository.geteventdate(change)
        }


    fun setdate(dateimport:String){
        if(__date.value==dateimport){
            return
        }
        __date.value=dateimport
        println(__date.value)
    }

    fun insert(word: Entries) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
    fun delete(word:Entries)=viewModelScope.launch(Dispatchers.IO) {
        repository.deletentry(word)
    }
    fun insertevent(event:Event)=viewModelScope.launch (Dispatchers.IO){
        repository.insertevent(event)
    }




}




