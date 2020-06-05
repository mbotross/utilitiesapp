package com.example.kotlin1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entry_table")
class Entries (@PrimaryKey @ColumnInfo(name = "Entry") val entry: String)