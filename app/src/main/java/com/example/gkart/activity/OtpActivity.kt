package com.example.gkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gkart.MainActivity
import com.example.gkart.R
import com.example.gkart.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var actionBar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "OTP"

        binding.btnVerify.setOnClickListener {
            if (binding.userOtp.text!!.isEmpty())

                Toast.makeText(this, "Enter Otp", Toast.LENGTH_SHORT).show()
            else {
                verifyUser(binding.userOtp.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {


        val credential =
            PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, otp)

        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)

                    val editor = preferences.edit()
                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }else{

                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
