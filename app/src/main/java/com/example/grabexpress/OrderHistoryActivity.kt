package com.example.grabexpress

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var tvEmptyOrders: TextView
    private lateinit var orderManager: OrderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        orderManager = OrderManager(this)

        rvOrders = findViewById(R.id.rv_orders)
        tvEmptyOrders = findViewById(R.id.tv_empty_orders)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val orders = orderManager.getAllOrders()
        rvOrders.layoutManager = LinearLayoutManager(this)

        if (orders.isEmpty()) {
            rvOrders.visibility = android.view.View.GONE
            tvEmptyOrders.visibility = android.view.View.VISIBLE
        } else {
            rvOrders.visibility = android.view.View.VISIBLE
            tvEmptyOrders.visibility = android.view.View.GONE
            rvOrders.adapter = OrderHistoryAdapter(orders)
        }
    }
}

class OrderHistoryAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    class ViewHolder(val view: android.view.View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tv_order_id)
        val tvDate: TextView = view.findViewById(R.id.tv_order_date)
        val tvItems: TextView = view.findViewById(R.id.tv_order_items)
        val tvTotal: TextView = view.findViewById(R.id.tv_order_total)
        val tvStatus: TextView = view.findViewById(R.id.tv_order_status)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

        holder.tvOrderId.text = "Order #${order.id}"
        holder.tvDate.text = dateFormat.format(Date(order.timestamp))
        holder.tvItems.text = "${order.items.size} item(s): ${order.items.joinToString { it.name }}"
        holder.tvTotal.text = "₱${order.total}"
        holder.tvStatus.text = order.status

        when (order.status) {
            "Pending" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
            "Preparing" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#2196F3"))
            "Delivered" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        }
    }

    override fun getItemCount(): Int = orders.size
}