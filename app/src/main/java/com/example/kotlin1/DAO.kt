package com.example.kotlin1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DAO{
    @Query("SELECT * from Entry_table")
    fun getEntries(): LiveData<List<Entries>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Entries)
    @Query("DELETE FROM Entry_table")
    suspend fun deleteAll()
    @Delete
    suspend fun deletentry(entry:Entries)
    @Query("SELECT * from Calendar_table")
    fun getEvents():LiveData<List<Event>>
    @Query("SELECT * from Calendar_table WHERE date = :date")
    fun getEventdate(date:String): LiveData<List<Event>>
   @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertevent(event:Event)
    @Delete
    suspend fun deletevent(event:Event)

}
