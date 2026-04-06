package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.grab)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnMobile = findViewById<MaterialButton>(R.id.btn_mobile)
        val btnGoogle = findViewById<MaterialButton>(R.id.btn_google)
        val btnFacebook = findViewById<MaterialButton>(R.id.btn_facebook)
        val tvForgotPass = findViewById<TextView>(R.id.tv_forgot_password)
        val btnCreateAccount = findViewById<MaterialButton>(R.id.btn_create_account)

        // → LoginActivity
        btnMobile.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // → SignUpActivity
        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google Sign In - Coming Soon", Toast.LENGTH_SHORT).show()
        }

        btnFacebook.setOnClickListener {
            Toast.makeText(this, "Facebook Sign In - Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }
}