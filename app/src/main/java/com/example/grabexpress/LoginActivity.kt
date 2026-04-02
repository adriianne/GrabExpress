package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_login_layout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etContact = findViewById<TextInputEditText>(R.id.et_contact)
        val btnSubmit = findViewById<Button>(R.id.btn_submit_pin)
        val btnBack   = findViewById<ImageButton>(R.id.btn_back)
        val tvSignUp  = findViewById<TextView>(R.id.tv_signup)

        // Hide PIN-related views since we're not using them
        val tilPin = findViewById<TextInputLayout>(R.id.til_pin)
        val btnVerify = findViewById<Button>(R.id.btn_verify_pin)
        val tvResend = findViewById<TextView>(R.id.tv_resend_pin)

        tilPin.visibility = android.view.View.GONE
        btnVerify.visibility = android.view.View.GONE
        tvResend.visibility = android.view.View.GONE

        // Change button text
        btnSubmit.text = "Continue"

        // Back → MainActivity
        btnBack.setOnClickListener {
            finish()
        }

        // Enable/Disable Continue button based on valid number
        etContact.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                if (input.length == 11 && input.startsWith("09")) {
                    btnSubmit.isEnabled = true
                    btnSubmit.backgroundTintList =
                        android.content.res.ColorStateList.valueOf(
                            android.graphics.Color.parseColor("#01B14F")
                        )
                } else {
                    btnSubmit.isEnabled = false
                    btnSubmit.backgroundTintList =
                        android.content.res.ColorStateList.valueOf(
                            android.graphics.Color.parseColor("#CCCCCC")
                        )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

// Continue button - Direct navigation to Dashboard
        btnSubmit.setOnClickListener {
            val contact = etContact.text.toString().trim()
            if (contact.length == 11 && contact.startsWith("09")) {
                // Navigate to DashboardActivity
                val dashboardIntent = Intent(this, DashboardActivity::class.java)
                dashboardIntent.putExtra("contact_number", contact)
                dashboardIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(dashboardIntent)
                finish()
            } else {
                Toast.makeText(this,
                    "Please enter a valid number (09XXXXXXXXX)",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // → SignUpActivity
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}