package com.example.kucingku.view.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.data.User
import com.example.kucingku.databinding.ActivitySignUpBinding
import com.example.kucingku.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvLoginhere.setOnClickListener {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val user = binding.username.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (user.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        saveToDatabase(firebaseAuth.uid.orEmpty(), email, user)
                        val intent = Intent(this , LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveToDatabase(uid: String, email: String, username: String) {
        if (uid.isNotEmpty()) {
            val ref = FirebaseDatabase.getInstance().reference
            val newUser = User(
                email = email,
                username = username
            )
            ref.child("user").child(uid).setValue(newUser)
        }
    }
}
