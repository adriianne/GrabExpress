package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_dashboard_layout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            // Header views
            val tvUsername = findViewById<TextView>(R.id.tv_username)
            val tvGreeting = findViewById<TextView>(R.id.tv_greeting)

            // Get phone number
            val contactNumber = intent.getStringExtra("contact_number") ?: "User"
            tvUsername.text = contactNumber

            // Set greeting based on time
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            tvGreeting.text = when {
                hour < 12 -> "Good Morning! 👋"
                hour < 17 -> "Good Afternoon! 👋"
                else -> "Good Evening! 👋"
            }

            // Bottom Navigation
            val navHome = findViewById<LinearLayout>(R.id.nav_home)
            val navOrders = findViewById<LinearLayout>(R.id.nav_orders)
            val navProfile = findViewById<LinearLayout>(R.id.nav_profile)

            val tvHomeText = findViewById<TextView>(R.id.tv_home_text)
            val tvOrdersText = findViewById<TextView>(R.id.tv_orders_text)
            val tvProfileText = findViewById<TextView>(R.id.tv_profile_text)

            // Setup bottom navigation - ADDED INTENTS
            navHome.setOnClickListener {
                tvHomeText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                tvOrdersText.setTextColor(resources.getColor(android.R.color.darker_gray))
                tvProfileText.setTextColor(resources.getColor(android.R.color.darker_gray))
                // Already on Home, just refresh
              //  Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }

            navOrders.setOnClickListener {
                tvOrdersText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                tvHomeText.setTextColor(resources.getColor(android.R.color.darker_gray))
                tvProfileText.setTextColor(resources.getColor(android.R.color.darker_gray))

                // GO TO ORDERS ACTIVITY
                val intent = Intent(this, OrdersActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
            }

            navProfile.setOnClickListener {
                tvProfileText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                tvHomeText.setTextColor(resources.getColor(android.R.color.darker_gray))
                tvOrdersText.setTextColor(resources.getColor(android.R.color.darker_gray))

                // GO TO PROFILE ACTIVITY
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
            }

            // Search functionality
            val etSearch = findViewById<EditText>(R.id.et_search)
            etSearch?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                    val query = etSearch.text.toString()
                    if (query.isNotEmpty()) {
                        Toast.makeText(this, "Searching for: $query", Toast.LENGTH_SHORT).show()
                    }
                    true
                } else false
            }

            // Category clicks
            findViewById<LinearLayout>(R.id.layout_fast_food)?.setOnClickListener {
                Toast.makeText(this, "Fast Food Category", Toast.LENGTH_SHORT).show()
            }
            findViewById<LinearLayout>(R.id.layout_drinks)?.setOnClickListener {
                Toast.makeText(this, "Drinks Category", Toast.LENGTH_SHORT).show()
            }
            findViewById<LinearLayout>(R.id.layout_pizza)?.setOnClickListener {
                Toast.makeText(this, "Pizza Category", Toast.LENGTH_SHORT).show()
            }
            findViewById<LinearLayout>(R.id.layout_desserts)?.setOnClickListener {
                Toast.makeText(this, "Desserts Category", Toast.LENGTH_SHORT).show()
            }

            // Order buttons - Add to cart
            findViewById<Button>(R.id.btn_order_1)?.setOnClickListener {
                val item = CartItem("Spicy Chicken Burger", 199, "Burger King")
                CartManager.addItem(item)
                Toast.makeText(this, "Added to cart: Spicy Chicken Burger (₱199)", Toast.LENGTH_SHORT).show()
            }
            findViewById<Button>(R.id.btn_order_2)?.setOnClickListener {
                val item = CartItem("Pepperoni Pizza", 299, "Pizza Hut")
                CartManager.addItem(item)
                Toast.makeText(this, "Added to cart: Pepperoni Pizza (₱299)", Toast.LENGTH_SHORT).show()
            }
            findViewById<Button>(R.id.btn_order_3)?.setOnClickListener {
                val item = CartItem("Iced Caramel Macchiato", 159, "Starbucks")
                CartManager.addItem(item)
                Toast.makeText(this, "Added to cart: Iced Caramel Macchiato (₱159)", Toast.LENGTH_SHORT).show()
            }

            // Promo button
            findViewById<Button>(R.id.btn_order_now)?.setOnClickListener {
                Toast.makeText(this, "Special Offer: 50% OFF on first order!", Toast.LENGTH_LONG).show()
            }

            Toast.makeText(this, "Welcome to Grab Express!", Toast.LENGTH_SHORT).show()

            // Cart button - Navigate to Cart screen
            findViewById<ImageButton>(R.id.btn_cart)?.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}