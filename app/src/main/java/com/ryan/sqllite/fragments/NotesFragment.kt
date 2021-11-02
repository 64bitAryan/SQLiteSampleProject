package com.ryan.sqllite.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ryan.sqllite.R
import com.ryan.sqllite.databinding.FragmentNotesBinding

class NotesFragment: Fragment(R.layout.fragment_notes) {
    private lateinit var binding: FragmentNotesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)

        binding.apply {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_notesFragment_to_dataFragment)
            }
        }

    }
}