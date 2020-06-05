package com.example.kotlin1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO{
    @Query("SELECT * from Entry_table ORDER BY Entry ASC")
    fun getEntries(): LiveData<List<Entries>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Entries)
    @Query("DELETE FROM Entry_table")
    suspend fun deleteAll()
}