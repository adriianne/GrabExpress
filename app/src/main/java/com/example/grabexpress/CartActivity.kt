package com.example.grabexpress

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private lateinit var tvEmptyCart: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var orderManager: OrderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize views
        rvCart = findViewById(R.id.rv_cart)
        tvTotal = findViewById(R.id.tv_total)
        btnCheckout = findViewById(R.id.btn_checkout)
        tvEmptyCart = findViewById(R.id.tv_empty_cart)
        btnBack = findViewById(R.id.btn_back)

        orderManager = OrderManager(this)

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        updateUI()

        btnCheckout.setOnClickListener {
            checkout()
        }
    }

    private fun setupRecyclerView() {
        rvCart.layoutManager = LinearLayoutManager(this)
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val items = CartManager.getItems()
        if (items.isEmpty()) {
            rvCart.visibility = android.view.View.GONE
            tvEmptyCart.visibility = android.view.View.VISIBLE
            btnCheckout.isEnabled = false
        } else {
            rvCart.visibility = android.view.View.VISIBLE
            tvEmptyCart.visibility = android.view.View.GONE
            btnCheckout.isEnabled = true
            rvCart.adapter = CartAdapter(items) { item, quantity ->
                CartManager.updateQuantity(item, quantity)
                updateUI()
            }
        }
    }

    private fun updateUI() {
        updateRecyclerView()
        tvTotal.text = "Subtotal: ₱${CartManager.getTotal()}"
    }

    private fun checkout() {
        val items = CartManager.getItems()
        if (items.isEmpty()) return

        val firstItem = items.first()
        val orderId = "ORD-${System.currentTimeMillis()}"
        val timestamp = System.currentTimeMillis()

        val subtotal = CartManager.getTotal()
        val deliveryFee = 49
        val total = subtotal + deliveryFee

        val order = Order(
            id = orderId,
            items = items.toList(),
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            total = total,
            restaurant = firstItem.restaurant,
            status = "Pending",
            timestamp = timestamp,
            estimatedDelivery = "30-45 min"
        )

        orderManager.saveOrder(order)
        CartManager.clearCart()

        Toast.makeText(this, "✅ Order placed!\nSubtotal: ₱$subtotal\nDelivery: ₱$deliveryFee\nTotal: ₱$total", Toast.LENGTH_LONG).show()
        finish()
    }
}

class CartAdapter(
    private val items: List<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val view: android.view.View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_cart_item_name)
        val tvPrice: TextView = view.findViewById(R.id.tv_cart_item_price)
        val tvQuantity: TextView = view.findViewById(R.id.tv_cart_quantity)
        val btnMinus: Button = view.findViewById(R.id.btn_minus)
        val btnPlus: Button = view.findViewById(R.id.btn_plus)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = "${item.name} (${item.restaurant})"
        holder.tvPrice.text = "₱${item.price}"
        holder.tvQuantity.text = item.quantity.toString()

        holder.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                onQuantityChanged(item, item.quantity - 1)
            } else {
                onQuantityChanged(item, 0)
            }
        }

        holder.btnPlus.setOnClickListener {
            onQuantityChanged(item, item.quantity + 1)
        }
    }

    override fun getItemCount(): Int = items.size
}