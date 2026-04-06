package com.example.grabexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class OrdersActivity : AppCompatActivity() {

    private lateinit var btnHome: LinearLayout
    private lateinit var btnOrders: LinearLayout
    private lateinit var btnProfile: LinearLayout
    private lateinit var rvOrders: RecyclerView
    private lateinit var emptyOrdersLayout: LinearLayout
    private lateinit var btnStartOrdering: Button
    private lateinit var orderManager: OrderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grab_order_layout)

        try {
            // Get views
            btnHome = findViewById(R.id.nav_home)
            btnOrders = findViewById(R.id.nav_orders)
            btnProfile = findViewById(R.id.nav_profile)
            rvOrders = findViewById(R.id.rv_orders_list)
            emptyOrdersLayout = findViewById(R.id.empty_orders_layout)
            btnStartOrdering = findViewById(R.id.btn_start_ordering)

            orderManager = OrderManager(this)
            val contactNumber = intent.getStringExtra("contact_number") ?: ""

            // Add this in onCreate() after initializing views
            val btnBack = findViewById<ImageButton>(R.id.btn_back)
            btnBack.setOnClickListener {
                finish()  // Goes back to Dashboard
            }

            // Setup bottom navigation
            btnHome.setOnClickListener {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
                finish()
            }

            btnOrders.setOnClickListener {
                Toast.makeText(this, "Orders", Toast.LENGTH_SHORT).show()
            }

            btnProfile.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
                finish()
            }

            btnStartOrdering.setOnClickListener {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("contact_number", contactNumber)
                startActivity(intent)
                finish()
            }


            // Load and display orders
            loadOrders()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadOrders() {
        val orders = orderManager.getAllOrders()

        if (orders.isEmpty()) {
            // Show empty state
            rvOrders.visibility = android.view.View.GONE
            emptyOrdersLayout.visibility = android.view.View.VISIBLE
        } else {
            // Show orders list
            rvOrders.visibility = android.view.View.VISIBLE
            emptyOrdersLayout.visibility = android.view.View.GONE
            rvOrders.layoutManager = LinearLayoutManager(this)
            rvOrders.adapter = OrdersAdapter(orders)
        }
    }

    // Adapter for displaying orders
    inner class OrdersAdapter(private val orders: List<Order>) :
        RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

        inner class OrderViewHolder(val view: android.view.View) : RecyclerView.ViewHolder(view) {
            val tvOrderId: TextView = view.findViewById(R.id.tv_order_id)
            val tvDate: TextView = view.findViewById(R.id.tv_order_date)
            val tvItems: TextView = view.findViewById(R.id.tv_order_items)
            val tvTotal: TextView = view.findViewById(R.id.tv_order_total)
            val tvStatus: TextView = view.findViewById(R.id.tv_order_status)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): OrderViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(view)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            val order = orders[position]
            val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

            holder.tvOrderId.text = "Order #${order.id}"
            holder.tvDate.text = dateFormat.format(Date(order.timestamp))

            // Show items with quantities
            val itemsWithQuantity = order.items.joinToString { "${it.name} x${it.quantity}" }
            holder.tvItems.text = "${order.items.size} item(s): $itemsWithQuantity"

            // Show full price breakdown
            holder.tvTotal.text = "Subtotal: ₱${order.subtotal}\nDelivery: ₱${order.deliveryFee}\nTotal: ₱${order.total}"

            holder.tvStatus.text = order.status

            when (order.status) {
                "Pending" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                "Preparing" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#2196F3"))
                "Delivered" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
            }
        }

        override fun getItemCount(): Int = orders.size
    }
}