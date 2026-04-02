package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_signup_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Using correct et_ IDs not til_ IDs
        val etFullName    = findViewById<TextInputEditText>(R.id.et_fullname)
        val etEmail       = findViewById<TextInputEditText>(R.id.et_email)
        val etMobile      = findViewById<TextInputEditText>(R.id.et_mobile)
        val etPassword    = findViewById<TextInputEditText>(R.id.et_password)
        val etConfirmPass = findViewById<TextInputEditText>(R.id.et_confirm_password)
        val cbTerms       = findViewById<CheckBox>(R.id.cb_terms)
        val btnSignUp     = findViewById<Button>(R.id.btn_signup)
        val btnBack       = findViewById<ImageButton>(R.id.btn_back)
        val tvLogin       = findViewById<TextView>(R.id.tv_login)

        // Back → MainActivity
        btnBack.setOnClickListener {
            finish()
        }

        // Create Account
        btnSignUp.setOnClickListener {
            val fullName    = etFullName.text.toString().trim()
            val email       = etEmail.text.toString().trim()
            val mobile      = etMobile.text.toString().trim()
            val password    = etPassword.text.toString().trim()
            val confirmPass = etConfirmPass.text.toString().trim()

            when {
                fullName.isEmpty() -> {
                    Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
                mobile.isEmpty() -> {
                    Toast.makeText(this, "Please enter your mobile number", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                password != confirmPass -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                !cbTerms.isChecked -> {
                    Toast.makeText(this, "Please agree to Terms and Conditions", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }

        // → LoginActivity
        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}