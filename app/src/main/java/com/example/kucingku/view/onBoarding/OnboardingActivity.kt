package com.example.kucingku.view.onBoarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.LoadingDialog
import com.example.kucingku.data.User
import com.example.kucingku.databinding.ActivityOnboardingBinding
import com.example.kucingku.utils.PrefsHelper
import com.example.kucingku.view.login.LoginActivity
import com.example.kucingku.view.userpref.UserPrefActivity
import com.example.kucingku.view.welcome.WelcomeActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingDialog = LoadingDialog(this)

        binding.btnStart.setOnClickListener {
            loadingDialog.show()
            val uid = PrefsHelper.getString(PrefsHelper.UID, "")
            if (uid.isNotEmpty()) {
                val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
                ref.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null && (user.breedsInterest.isNullOrEmpty().not() || user.ageInterest.isNullOrEmpty().not() || user.genderInterest.isNullOrEmpty().not())) {
                            startActivity(Intent(this@OnboardingActivity, WelcomeActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                            finish()
                        }
                        loadingDialog.dismiss()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                        finish()
                        loadingDialog.dismiss()
                    }
                })
            } else {
                startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                finish()
                loadingDialog.dismiss()
            }
        }
    }
}
