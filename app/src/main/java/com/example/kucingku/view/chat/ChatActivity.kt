package com.example.kucingku.view.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}