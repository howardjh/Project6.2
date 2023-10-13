package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project6.Note
import com.example.project6.NoteDiffItemCallback
import com.example.project6.databinding.NoteItemBinding
class NoteItemAdapter(
    private val clickListener: (noteId: Long) -> Unit,
    private val deleteClickListener: (noteId: Long) -> Unit)
    : ListAdapter<Note, NoteItemAdapter.NoteItemViewHolder>(NoteDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NoteItemViewHolder = NoteItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener)
    }

    class NoteItemViewHolder(private val binding: NoteItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): NoteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return NoteItemViewHolder(binding)
            }
        }

        fun bind(item: Note, clickListener: (noteId: Long) -> Unit,
                 deleteClickListener: (noteId: Long) -> Unit) {
            binding.note = item
            binding.root.setOnClickListener { clickListener(item.noteId) }
            binding.deleteButton.setOnClickListener { deleteClickListener(item.noteId) }
        }
    }
}
