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
    private var dateimport:String
    private var time:String



    init{
        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = DataRepo(wordsDao)
        allWords = repository.allWords
        allEvents=repository.allevents
        dateimport=repository.dateimport
        __date = MutableLiveData()
        time=repository.time


    }



        val date= Transformations
        .switchMap(__date){change->
            println("called")
            repository.geteventdate(change)
        }

    fun getdate():String{
        return dateimport
    }
    fun setnewdate(newdate:String){
        dateimport=newdate

    }
    fun gettime():String{
        return time
    }
    fun settime(time:String){
        this.time=time
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
    fun deletevent(event:Event)=viewModelScope.launch (Dispatchers.IO){
        repository.deletevent(event)
    }




}




