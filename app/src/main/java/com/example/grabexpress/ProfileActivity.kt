package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_profile_layout)

        try {
            // Bottom navigation
            val btnHome = findViewById<LinearLayout>(R.id.nav_home)
            val btnOrders = findViewById<LinearLayout>(R.id.nav_orders)
            val btnProfile = findViewById<LinearLayout>(R.id.nav_profile)
            val btnLogout = findViewById<Button>(R.id.btn_logout)

            val contactNumber = intent.getStringExtra("contact_number") ?: "User"

            // Set profile info
            findViewById<TextView>(R.id.tv_profile_phone)?.text = contactNumber
            findViewById<TextView>(R.id.tv_profile_name)?.text = "User"
            findViewById<TextView>(R.id.tv_full_name)?.text = "Full Name: Not set"
            findViewById<TextView>(R.id.tv_email)?.text = "Email: Not set"
            findViewById<TextView>(R.id.tv_member_since)?.text = "Member Since: 2024"

            // Bottom navigation
            btnHome.setOnClickListener {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
                finish()
            }

            btnOrders.setOnClickListener {
                val intent = Intent(this, OrdersActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
                finish()
            }

            btnProfile.setOnClickListener {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            }

            // Settings click listeners
            findViewById<TextView>(R.id.tv_edit_profile)?.setOnClickListener {
                Toast.makeText(this, "Edit Profile - Coming Soon", Toast.LENGTH_SHORT).show()
            }

            findViewById<TextView>(R.id.tv_change_password)?.setOnClickListener {
                Toast.makeText(this, "Change Password - Coming Soon", Toast.LENGTH_SHORT).show()
            }

            findViewById<TextView>(R.id.tv_help_center)?.setOnClickListener {
                startActivity(Intent(this, SupportActivity::class.java))
            }

            findViewById<TextView>(R.id.tv_terms)?.setOnClickListener {
                startActivity(Intent(this, TermsActivity::class.java))
            }

            // Logout
            btnLogout.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}