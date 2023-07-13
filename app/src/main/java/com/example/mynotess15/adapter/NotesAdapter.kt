package com.example.mynotess15.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotess15.R
import com.example.mynotess15.database.Note
import com.example.mynotess15.databinding.EachItemBinding

class NotesAdapter(val context: Context,private var onClickListener: OnClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewGroup>() {

    private lateinit var binding: EachItemBinding
    private var noteList = ArrayList<Note>();

    class ViewGroup(binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tempTitle: TextView = binding.textViewTitle
        private val tempDescription: TextView = binding.textViewDescription
        private val tempDate: TextView = binding.textViewDate
        val more: ImageView = binding.ivMore
        val layout: ConstraintLayout = binding.background

        fun bind(note: Note, context: Context) {
            tempTitle.text = note.title
            tempDescription.text = note.description
            tempDate.text = note.date
            stringToColor(note.color)

            val startColor = ContextCompat.getColor(context, stringToColor(note.color))
            var endColor = ContextCompat.getColor(context, R.color.white)

            setBackgroundGradient(layout, startColor, endColor)
        }

        fun setBackgroundGradient(view: View, startColor: Int, endColor: Int) {
            Log.d("TAG", "setBackgroundGradient: startColor: ${startColor}   endColor $endColor")
            // Create a GradientDrawable with the two colors.
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
            )
            // Set the background of the view to the gradient.
            view.background = gradientDrawable
        }

        private fun stringToColor(color: String) : Int {
            return when(color){
                "red" -> R.color.red
                "green" -> R.color.green
                "grey" -> R.color.grey
                "orange" -> R.color.orange
                "pink" -> R.color.pink
                "sky" -> R.color.sky
                "yellow" -> R.color.yellow
                else -> {
                     R.color.greyLight
                }
            }
        }



    }


    fun getNoteList(): ArrayList<Note> {
        return noteList
    }

    fun updateList(list: ArrayList<Note>) {
        Log.d("TAG", "updateList: ")
        noteList.clear()
        noteList = list
//        notifyItemRangeInserted(0,list.size - 1)
        notifyDataSetChanged()
    }

    fun addSingleNote(list: ArrayList<Note>) {
        Log.d("TAG", "addSingleNote: ")
        val newNote = list[list.size - 1]
        noteList.add(newNote)
        notifyItemInserted(list.size)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewGroup {
        binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewGroup(binding)
    }

    override fun onBindViewHolder(holder: ViewGroup, position: Int) {
        val note = noteList[position]
        holder.bind(note,context)
        holder.itemView.setOnClickListener {
            onClickListener.onNoteClicked(note)
        }
        holder.more.setOnClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.inflate(R.menu.each_item_more_info)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        onClickListener.onDeleteClicked(note)
                    }
                }
                true
            }
            popupMenu.show()
        }

    }



    override fun getItemCount(): Int {
        return noteList.size;
    }

    interface OnClickListener {
        fun onNoteClicked(note: Note)
        fun onDeleteClicked(note: Note)
    }
}

