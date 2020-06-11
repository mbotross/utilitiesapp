package com.example.kotlin1

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "Entry_table")
public class Entries (@PrimaryKey   val id: Int,
                      @NonNull
                      @ColumnInfo(name = "Entry")
                      val entry: String)

@Entity(tableName = "Calendar_table")
public class Event(@PrimaryKey
                   @NonNull
                    @ColumnInfo(name="Event")
                    val event:String,
                    @ColumnInfo(name="start")
                    val start:Int,
                   @ColumnInfo(name="end")
                    val end:Int,
                   @ColumnInfo(name="date")
                   var date:String,
                   @ColumnInfo(name="time1")
                    var time1:String,
                    @ColumnInfo(name="time2")
                    var time2:String)