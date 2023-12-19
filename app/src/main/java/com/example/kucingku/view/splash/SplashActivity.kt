package com.example.kucingku.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.R
import com.example.kucingku.view.onBoarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            goToOnboardingActivity()
        }, 3000L)
    }

    private fun goToOnboardingActivity() {
        Intent(this, OnboardingActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
