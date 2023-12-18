package com.example.kucingku.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.kucingku.view.main.MainActivity
import com.example.kucingku.R
import com.example.kucingku.data.pref.UserModel
import com.example.kucingku.databinding.ActivityLoginBinding
import com.example.kucingku.utils.ResultState

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun OnCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.contentDescription = getString(R.string.btn_login_description)
            emailEditTextLayout.contentDescription = getString(R.string.add_email_description)
            passwordEditTextLayout.contentDescription = getString(R.string.add_password_description)
        }
        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            with(binding) {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()

                viewModel.login(email, password).observe(this@LoginActivity) { response ->
                    when (response) {
                        ResultState.Loading -> {
                            progressBar.isVisible = true
                        }

                        is ResultState.Error -> {
                            progressBar.isVisible = false
                            showToast(response.error)
                        }

                        is ResultState.Success -> {
                            progressBar.isVisible = false
                            viewModel.saveSession(
                                UserModel(
                                    email,
                                    response.data.loginResult?.token.toString(),
                                    true
                                )
                            )
                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle(getString(R.string.congratulation))
                                setMessage(getString(R.string.login_success))
                                setPositiveButton(getString(R.string.next)) { _, _ ->
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}