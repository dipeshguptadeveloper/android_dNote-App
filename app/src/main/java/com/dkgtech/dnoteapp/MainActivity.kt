package com.dkgtech.dnoteapp

import android.app.Dialog
import android.graphics.Color
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dkgtech.dnoteapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var noteAdapter: RecyclerNoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appDB = NoteDatabase.getDatabase(this@MainActivity)
//        appDB.NoteDao().addNote(NoteModel(0, "Hey", "Hello dear, how are you?"))
//        appDB.NoteDao().addNote(NoteModel(0,"IMS Data Report", "IMS report to be share on every Monday before 02 O'Clock "))
//        appDB.NoteDao().addNote(NoteModel(0,"Android Meet", "Meeting at 11 O'Clock"))
//        appDB.NoteDao().addNote(NoteModel(0, "MBR Meet", "Meeting is postponed till 20th June, 2023"))

        val listNotes = appDB.NoteDao().getAllNotes()

//       val deleteNote = appDB.NoteDao().deleteNote(2)

        binding.rcViewNotes.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        noteAdapter = RecyclerNoteAdapter(this@MainActivity, listNotes)
        binding.rcViewNotes.adapter = noteAdapter


        binding.btnFab.setOnClickListener {
            val noteAddDialog = Dialog(this@MainActivity).apply {
                setContentView(R.layout.note_add)
                setCancelable(false)

                val edtNoteTitle = findViewById<EditText>(R.id.edtNoteTitle)
                val edtNoteDesc = findViewById<EditText>(R.id.edtNoteDesc)
                val btnAdd = findViewById<Button>(R.id.btnAdd)
                val btnCancel = findViewById<Button>(R.id.btnCancel)

                val noteTitle = edtNoteTitle.text.toString()
                val noteDesc = edtNoteDesc.text.toString()

                btnAdd.setOnClickListener {

                    if (noteTitle.isNotBlank() && noteDesc.isNotBlank()) {
                        appDB.NoteDao()
                            .addNote(NoteModel(0, noteTitle, noteDesc))
                        noteAdapter.notifyItemInserted(listNotes.size - 1)
                        binding.rcViewNotes.scrollToPosition(listNotes.size - 1)
                    }

                    dismiss()
                }

                btnCancel.setOnClickListener {
                    dismiss()
                }

            }
            noteAddDialog.show()
        }

    }

//    fun getRandomColor(): Int {
//        val alpha = 255
//        val red = Random.nextInt(256)
//        val green = Random.nextInt(256)
//        val blue = Random.nextInt(256)
//        return Color.argb(alpha, red, green, blue)
//    }
}