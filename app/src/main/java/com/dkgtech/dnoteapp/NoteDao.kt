package com.dkgtech.dnoteapp

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun addNote(note: NoteModel)

    @Query("select * from note")
    fun getAllNotes(): List<NoteModel>

    @Query("DELETE FROM note WHERE id=:id")
    fun deleteNote(id: Int)

    @Update
    fun updateNote(note: NoteModel)
}