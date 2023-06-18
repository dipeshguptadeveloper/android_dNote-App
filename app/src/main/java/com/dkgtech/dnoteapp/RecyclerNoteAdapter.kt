package com.dkgtech.dnoteapp

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerNoteAdapter(val context: Context, val arrNote: List<NoteModel>) :
    RecyclerView.Adapter<RecyclerNoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNoteTitle = itemView.findViewById<TextView>(R.id.txtNoteTitle)
        val txtNoteDesc = itemView.findViewById<TextView>(R.id.txtNoteDesc)
        val btnAdd = itemView.findViewById<Button>(R.id.btnAdd)
        val btnCancel = itemView.findViewById<Button>(R.id.btnCancel)
        val btnEditNote = itemView.findViewById<ImageView>(R.id.btnEditNote)
        val cardNote = itemView.findViewById<CardView>(R.id.cardNote)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_row, parent, false))
    }

    override fun getItemCount(): Int {
        return arrNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (context as MainActivity)

        holder.txtNoteTitle.text = arrNote[position].title
        holder.txtNoteDesc.text = arrNote[position].desc
        holder.cardNote.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(view: View?): Boolean {
                val deleteDialog = AlertDialog.Builder(context).apply {
                    setTitle("Delete Note?")
                    setMessage("Are you sure want to delete Note?")
                    setCancelable(false)
                    setIcon(R.drawable.baseline_delete_24)
                    setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val delete =
                                NoteDatabase.getDatabase(context).NoteDao().deleteNote(2)
                        }
                    })

                    setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }

                    })
                }
                deleteDialog.show()
                return true
            }
        })

        holder.btnEditNote.setOnClickListener {
            val updateDialog = Dialog(context)
            updateDialog.setContentView(R.layout.note_add)

            val txtTitle = updateDialog.findViewById<TextView>(R.id.txtTitle)
            val edtNoteTitle = updateDialog.findViewById<EditText>(R.id.edtNoteTitle)
            val edtNoteDesc = updateDialog.findViewById<EditText>(R.id.edtNoteDesc)
            val btnUpdate = updateDialog.findViewById<Button>(R.id.btnAdd)
            val btnCancel = updateDialog.findViewById<Button>(R.id.btnCancel)

            txtTitle.text = "Update Note"
            edtNoteTitle.setText(arrNote[position].title)
            edtNoteDesc.setText(arrNote[position].desc)
            btnUpdate.text = "Update"
            updateDialog.show()

            val noteTitle = edtNoteTitle.text.toString()
            val noteDesc = edtNoteDesc.text.toString()

            btnUpdate.setOnClickListener {
                NoteDatabase.getDatabase(context).NoteDao()
                    .updateNote(NoteModel(0, noteTitle, noteDesc))
                Log.d("new update", "$noteTitle, $noteDesc")
                Toast.makeText(context, "Note Updated Successfully", Toast.LENGTH_SHORT).show()
                updateDialog.dismiss()
            }

            btnCancel.setOnClickListener {
                updateDialog.dismiss()
            }
        }

    }
}