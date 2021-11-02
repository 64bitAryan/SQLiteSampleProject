package com.ryan.sqllite.fragments

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ryan.sqllite.R
import com.ryan.sqllite.databinding.FragmentDataBinding
import com.ryan.sqllite.entity.DatabaseHandler
import com.ryan.sqllite.entity.Person

class DataFragment: Fragment(R.layout.fragment_data) {
    private lateinit var binding: FragmentDataBinding
    private lateinit var dbHandler: DatabaseHandler
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDataBinding.bind(view)
        dbHandler = DatabaseHandler(requireContext())

        binding.apply {
            saveBt.setOnClickListener {
                val firstName = firstNameEt.text.toString()
                val lastName = lastNameEt.text.toString()
                val age = ageEt.text.toString()

                if(age.isEmpty() || lastName.isEmpty() || firstName.isEmpty()) {
                    Toast.makeText(requireContext(), "Fill data in all Fields", Toast.LENGTH_LONG).show()
                } else {
                    val person = Person(
                        firstName = firstName,
                        lastName = lastName,
                        age = age
                    )
                    var res: Long = 0
                    try {
                        res = dbHandler.insetEmployee(person)
                        Log.d("DatabaseFragment", "Inserting....")
                    } catch(e: SQLiteException){
                        Log.d("DatabaseFragment", e.message.toString())
                    }
                    Log.d("DatabaseFragment", res.toString())
                    Log.d("DatabaseFragment", dbHandler.readEmployee().toString())
                }
            }
        }

    }
}