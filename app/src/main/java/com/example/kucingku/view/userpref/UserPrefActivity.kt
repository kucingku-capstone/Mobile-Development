package com.example.kucingku.view.userpref

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.databinding.ActivityUserPreferanceBinding
import com.example.kucingku.view.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class UserPrefActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPreferanceBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }


}