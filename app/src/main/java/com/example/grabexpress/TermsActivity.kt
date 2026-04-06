package com.example.grabexpress

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.setPadding(50, 30, 50, 30)
        textView.textSize = 14f
        textView.setTextColor(android.graphics.Color.parseColor("#333333"))
        textView.movementMethod = ScrollingMovementMethod()

        textView.text = """
            TERMS AND CONDITIONS
            
            Last updated: April 2026
            
            1. ACCEPTANCE OF TERMS
            By using GrabExpress, you agree to these terms.
            
            2. USER ACCOUNT
            You must provide accurate information when creating an account.
            You are responsible for maintaining the security of your account.
            
            3. ORDERING
            All orders are subject to availability.
            Prices are subject to change without notice.
            
            4. DELIVERY
            Delivery times are estimates only.
            We are not responsible for delays beyond our control.
            
            5. PAYMENT
            Payment must be made at time of order.
            Cash on Delivery available for select locations.
            
            6. CANCELLATIONS
            Orders can be cancelled within 5 minutes of placement.
            Contact support for cancellation requests.
            
            7. REFUNDS
            Refunds are processed within 5-7 business days.
            Contact support for refund requests.
            
            8. PRIVACY
            Your data is handled according to our Privacy Policy.
            
            9. MODIFICATIONS
            We reserve the right to modify these terms at any time.
            
            10. CONTACT
            For questions, contact support@grabrexpress.com
        """.trimIndent()

        setContentView(textView)
        supportActionBar?.title = "Terms & Conditions"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}