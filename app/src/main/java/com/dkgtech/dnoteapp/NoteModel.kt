package com.dkgtech.dnoteapp

import android.icu.text.CaseMap.Title
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "noteTitle") val title: String,
    @ColumnInfo(name = "noteDesc") val desc: String,
)
