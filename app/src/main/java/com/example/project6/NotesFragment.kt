package com.example.project6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.NoteItemAdapter
import com.example.project6.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    val TAG = "NotesFragment"
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao  // Get DAO and ViewModel
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        fun noteClicked (noteId : Long) {
            viewModel.onNoteClicked(noteId)
        }
        fun yesPressed(noteId : Long) {
            Log.d(TAG, "in yesPressed(): noteId = $noteId")
            binding.viewModel?.deleteNote(noteId)
        }
        fun deleteClicked (noteId : Long) {
            ConfirmDeleteDialogFragment(noteId,::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }
        fun titleClicked(noteId: Long){
            val navController = findNavController()
            val bundle = Bundle()
            bundle.putLong("noteId", noteId)
            navController.navigate(R.id.action_notesFragment_to_editNoteFragment, bundle)
        }




        val adapter = NoteItemAdapter(::titleClicked,::noteClicked,::deleteClicked)

        val recyclerView = binding.notesList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesList.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        binding.addNoteBtn.setOnClickListener {
            val args = Bundle()
            // Navigate to add note screen
            val newNote = viewModel.addNote()
            val newNoteId = newNote.noteId
            args.putLong("noteId", newNoteId)
            view.findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment, args)
            viewModel.onNoteNavigated()
            viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId ->
                noteId?.let {
                    /*
                    val action = NotesFragmentDirections
                        .actionTasksFragmentToEditTaskFragment(noteId)
                    this.findNavController().navigate(action)
                    */
                    view.findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment, args)
                    viewModel.onNoteNavigated()
                }
            })

        }




        return view
    }


}
