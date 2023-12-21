// Sesuaikan dengan nama package Anda
package com.example.kucingku.view.findCat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kucingku.databinding.FragmentFindCatBinding

class FindCatFragment : Fragment() {

    private lateinit var binding: FragmentFindCatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindCatBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
}
