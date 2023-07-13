package com.example.mynotess15.fragments

import android.net.EthernetNetworkSpecifier
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotess15.R
import com.example.mynotess15.adapter.NotesAdapter
import com.example.mynotess15.database.Note
import com.example.mynotess15.databinding.FragmentHomeBinding
import com.example.mynotess15.viewmodel.NotesViewModel


class HomeFragment : Fragment(), NotesAdapter.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NotesViewModel by activityViewModels()
    private lateinit var adapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        adapter = NotesAdapter(requireContext(),this)
        recyclerView = binding.recyclerView
        searchView = binding.searchView
//        val list = viewModel.getAllNotes().value
//        adapter.updateList(list as ArrayList<Note>)

        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            Log.d("TAG", "onCreateView: Observer called")
            Log.d("TAG", "onCreateView: list : $it")

            viewModel.saveAllNotes(ArrayList(it))
            adapter.updateList(ArrayList(it))

            if (!viewModel.isSearching) {
                viewModel.saveAllNotes(ArrayList(it))
                adapter.updateList(ArrayList(it))
            } else {
                val searchText = searchView.text.toString()
                val filteredList = viewModel.searchTask(searchText)
                adapter.updateList(filteredList as ArrayList<Note>)
            }

        }

        binding.buttonAddNote.setOnClickListener {
            toEditFragment()
        }

        searchNote()
    }

    private fun searchNote() {
        searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Call the searchTask function when the user types something in the EditText view
                Log.d("TAG", "onTextChanged: $s")

                val searchText = searchView.text.toString()
                val filteredList = viewModel.searchTask(searchText)

                // Store the search query and filtered list in the ViewModel
                viewModel.setSearchQuery(searchText)
                viewModel.setFilteredList(filteredList!!)

                if (viewModel.isSearching || viewModel.isLoading) {
                    adapter.updateList(filteredList as ArrayList<Note>)
                }

                viewModel.isSearching = viewModel.getSearchQuery().isNotEmpty()
                Log.d("TAG", "onTextChanged: is Searching : ${viewModel.isSearching}")

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun toEditFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_editFragment)
    }

    override fun onNoteClicked(note: Note) {
        Log.d("TAG", "onNoteClicked: ")
        viewModel.setTempNote(note)
        viewModel.setIsUpdate(true)
        toEditFragment()
    }

    override fun onDeleteClicked(note: Note) {
        Log.d("TAG", "onDeleteClicked: ")
        viewModel.delete(note)
    }

}