package com.example.grabexpress

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.setPadding(50, 50, 50, 50)

        // Title
        val title = TextView(this)
        title.text = "❓ Help Center"
        title.textSize = 24f
        title.setTextColor(android.graphics.Color.parseColor("#01B14F"))
        title.setPadding(0, 0, 0, 30)
        linearLayout.addView(title)

        // FAQ Section
        val faqTitle = TextView(this)
        faqTitle.text = "Frequently Asked Questions"
        faqTitle.textSize = 18f
        faqTitle.setPadding(0, 20, 0, 10)
        linearLayout.addView(faqTitle)

        val faqs = listOf(
            "❓ How do I place an order?" to "1. Browse restaurants\n2. Select your food\n3. Add to cart\n4. Checkout",
            "❓ How long does delivery take?" to "Delivery usually takes 30-45 minutes depending on location.",
            "❓ How can I track my order?" to "Go to Orders tab to see your order status.",
            "❓ What payment methods are accepted?" to "Cash on Delivery and Credit Card",
            "❓ How do I cancel my order?" to "Contact support immediately after placing order."
        )

        for ((question, answer) in faqs) {
            val q = TextView(this)
            q.text = question
            q.textSize = 16f
            q.setTextColor(android.graphics.Color.parseColor("#222222"))
            q.setPadding(0, 15, 0, 5)
            linearLayout.addView(q)

            val a = TextView(this)
            a.text = answer
            a.textSize = 14f
            a.setTextColor(android.graphics.Color.parseColor("#666666"))
            a.setPadding(20, 0, 0, 10)
            linearLayout.addView(a)
        }

        // Contact Section
        val contactTitle = TextView(this)
        contactTitle.text = "📞 Contact Us"
        contactTitle.textSize = 18f
        contactTitle.setPadding(0, 30, 0, 10)
        linearLayout.addView(contactTitle)

        val contact = TextView(this)
        contact.text = "Email: support@grabrexpress.com\nHotline: (02) 1234 5678\nLive Chat: Available 24/7"
        contact.textSize = 14f
        contact.setTextColor(android.graphics.Color.parseColor("#666666"))
        linearLayout.addView(contact)

        setContentView(linearLayout)
        supportActionBar?.title = "Help Center"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}