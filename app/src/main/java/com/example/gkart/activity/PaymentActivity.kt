package com.example.gkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gkart.MainActivity
import com.example.gkart.R
import com.example.gkart.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Payments"

        binding.btnCancel.setOnClickListener {
            Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.btnPay.setOnClickListener {
            Toast.makeText(this, "Thank you for shopping with us", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}