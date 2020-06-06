package com.example.kotlin1

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entry_table")
public class Entries (@PrimaryKey   val id: Int,
                      @NonNull
                      @ColumnInfo(name = "Entry")
                      val entry: String)

