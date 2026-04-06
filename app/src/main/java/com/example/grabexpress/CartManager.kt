package com.example.grabexpress

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class CartItem(
    val name: String,
    val price: Int,
    val restaurant: String,
    val image: String = "🍔",
    var quantity: Int = 1
)

data class Order(
    val id: String,
    val items: List<CartItem>,
    val subtotal: Int,
    val deliveryFee: Int,
    val total: Int,
    val restaurant: String,
    val status: String,
    val timestamp: Long,
    val estimatedDelivery: String
)

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        val existing = cartItems.find { it.name == item.name }
        if (existing != null) {
            existing.quantity++
        } else {
            cartItems.add(item)
        }
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun updateQuantity(item: CartItem, quantity: Int) {
        if (quantity <= 0) {
            cartItems.remove(item)
        } else {
            item.quantity = quantity
        }
    }

    fun getItems(): List<CartItem> = cartItems.toList()

    fun getTotal(): Int = cartItems.sumOf { it.price * it.quantity }

    fun clearCart() = cartItems.clear()

    fun getItemCount(): Int = cartItems.sumOf { it.quantity }
}

class OrderManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveOrder(order: Order) {
        val orders = getAllOrders().toMutableList()
        orders.add(0, order)
        val json = gson.toJson(orders)
        prefs.edit().putString("order_list", json).apply()
    }

    fun getAllOrders(): List<Order> {
        val json = prefs.getString("order_list", "[]")
        val type = object : TypeToken<List<Order>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getOrderCount(): Int = getAllOrders().size
}