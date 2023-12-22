//package com.example.kucingku.view.chat
//
//import ChatAdapter
//import android.Manifest
//import android.os.Build
//import android.os.Bundle
//import android.os.Message
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.kucingku.R
//import com.example.kucingku.databinding.ActivityChatBinding
//import com.firebase.ui.database.FirebaseRecyclerOptions
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import java.util.Date
//
class ChatActivity {}
// : AppCompatActivity() {
//
//    private lateinit var binding: ActivityChatBinding
//    private lateinit var auth: FirebaseAuth
//
//    private lateinit var db: FirebaseDatabase
//    private lateinit var adapter: ChatAdapter
//
//    private val requestNotificationPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityChatBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (Build.VERSION.SDK_INT >= 33) {
//            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//        }
//
//        auth = Firebase.auth
//        val firebaseUser = auth.currentUser
//
//        db = Firebase.database
//
//        val messagesRef = db.reference.child(MESSAGES_CHILD)
//
//        binding.sendButton.setOnClickListener {
//            val friendlyMessage = com.example.kucingku.data.Message(
//                binding.messageEditText.text.toString(),
//                firebaseUser?.displayName.toString(),
//                firebaseUser?.photoUrl.toString(),
//                Date().time
//            )
//            messagesRef.push().setValue(friendlyMessage) { error, _ ->
//                if (error != null) {
//                    Toast.makeText(this, getString(R.string.send_error) + error.message, Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_SHORT).show()
//                }
//            }
//            binding.messageEditText.setText("")
//        }
//
//        val manager = LinearLayoutManager(this)
//        manager.stackFromEnd = true
//        binding.messageRecyclerView.layoutManager = manager
//
//        val options = FirebaseRecyclerOptions.Builder<Message>()
//            .setQuery(messagesRef, Message::class.java)
//            .build()
//        if (firebaseUser != null) {
//            adapter = ChatAdapter(options, firebaseUser.displayName)
//        }
//        binding.messageRecyclerView.adapter = adapter
//    }
//    companion object {
//        const val MESSAGES_CHILD = "messages"
//    }
//}