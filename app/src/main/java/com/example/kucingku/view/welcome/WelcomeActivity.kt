package com.example.kucingku.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.databinding.ActivityWelcomeBinding
import com.example.kucingku.view.findCat.FindCatFragment

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFindACat.setOnClickListener {
            val intent = Intent(this, FindCatFragment::class.java)
            startActivity(intent)
        }
    }
}