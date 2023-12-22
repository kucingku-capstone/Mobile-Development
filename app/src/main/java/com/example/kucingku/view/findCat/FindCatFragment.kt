package com.example.kucingku.view.findCat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kucingku.databinding.FragmentFindCatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FindCatFragment : Fragment() {

    private lateinit var binding: FragmentFindCatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindCatBinding.inflate(inflater, container, false)
        val view = binding.root

        getData()

        return view
    }

    private fun getData() {
        FragmentFindCatBinding.inflate(layoutInflater)
        FirebaseDatabase.getInstance().getReference("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("SHUBH", "onDataChange: $snapshot")
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}