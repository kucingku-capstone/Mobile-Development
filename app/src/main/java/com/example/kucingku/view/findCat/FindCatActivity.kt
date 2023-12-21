package com.example.kucingku.view.findCat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.databinding.ActivityFindCatBinding

class FindCatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindCatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}