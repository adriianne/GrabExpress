package com.example.grabexpress

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var navHome: LinearLayout
    private lateinit var navOrders: LinearLayout
    private lateinit var navProfile: LinearLayout
    private lateinit var tvHomeText: TextView
    private lateinit var tvOrdersText: TextView
    private lateinit var tvProfileText: TextView
    private lateinit var contentArea: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_dashboard_layout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        tvUsername = findViewById(R.id.tv_username)
        tvGreeting = findViewById(R.id.tv_greeting)
        contentArea = findViewById(R.id.content_area)

        // Bottom Navigation Views
        navHome = findViewById(R.id.nav_home)
        navOrders = findViewById(R.id.nav_orders)
        navProfile = findViewById(R.id.nav_profile)

        // Get the text views for bottom navigation (using correct IDs)
        tvHomeText = findViewById(R.id.tv_home_text)
        tvOrdersText = findViewById(R.id.tv_orders_text)
        tvProfileText = findViewById(R.id.tv_profile_text)

        // Get phone number passed from LoginActivity
        val contactNumber = intent.getStringExtra("contact_number") ?: "User"
        tvUsername.text = contactNumber

        // Set greeting based on time
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        tvGreeting.text = when {
            hour < 12 -> "Good Morning! 👋"
            hour < 17 -> "Good Afternoon! 👋"
            else -> "Good Evening! 👋"
        }

        // Setup navigation
        setupNavigation()

        // Set default selected tab
        selectHomeTab()

        Toast.makeText(this, "Welcome to Grab Express!", Toast.LENGTH_SHORT).show()
    }

    private fun setupNavigation() {
        navHome.setOnClickListener {
            selectHomeTab()
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }

        navOrders.setOnClickListener {
            selectOrdersTab()
            Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show()
        }

        navProfile.setOnClickListener {
            selectProfileTab()
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectHomeTab() {
        // Update colors
        tvHomeText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        tvOrdersText.setTextColor(resources.getColor(android.R.color.darker_gray))
        tvProfileText.setTextColor(resources.getColor(android.R.color.darker_gray))

        // Show home content
        contentArea.text = """
            🏠 HOME
            
            Welcome to Grab Express!
            
            Special Offer: 50% OFF on first order! 🎉
            
            Popular Near You:
            
            • Spicy Chicken Burger - ₱199 ⭐4.5
              Burger King
            
            • Pepperoni Pizza - ₱299 ⭐4.8
              Pizza Hut
            
            • Iced Caramel Macchiato - ₱159 ⭐4.6
              Starbucks
        """.trimIndent()
    }

    private fun selectOrdersTab() {
        // Update colors
        tvOrdersText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        tvHomeText.setTextColor(resources.getColor(android.R.color.darker_gray))
        tvProfileText.setTextColor(resources.getColor(android.R.color.darker_gray))

        // Show orders content
        contentArea.text = """
            📦 MY ORDERS
            
            No orders yet.
            
            Ready to order? Check out our special offers!
            
            Quick Order:
            • Fast Food Delivery
            • Drinks & Beverages
            • Pizza & Pasta
            • Desserts & Sweets
            
            Track your orders here once you place them.
        """.trimIndent()
    }

    private fun selectProfileTab() {
        // Update colors
        tvProfileText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        tvHomeText.setTextColor(resources.getColor(android.R.color.darker_gray))
        tvOrdersText.setTextColor(resources.getColor(android.R.color.darker_gray))

        val contactNumber = intent.getStringExtra("contact_number") ?: "User"

        // Show profile content
        contentArea.text = """
            👤 MY PROFILE
            
            Phone: $contactNumber
            Member Since: 2024
            
            Account Settings:
            • Edit Profile
            • Change Password
            • Payment Methods
            • Saved Addresses
            
            Support:
            • Help Center
            • Terms & Conditions
            • Privacy Policy
        """.trimIndent()
    }
}