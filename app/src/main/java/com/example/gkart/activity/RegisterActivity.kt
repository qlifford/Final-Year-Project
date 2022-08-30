package com.example.gkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.gkart.MainActivity
import com.example.gkart.R
import com.example.gkart.databinding.ActivityRegisterBinding
import com.example.gkart.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Register"

        binding.btnLogin.setOnClickListener {
           openLogin()
        }
        binding.btnReg.setOnClickListener {
            validateUser()
        }
    }
    private fun validateUser() {
        if (binding.userName.text!!.isEmpty() || binding.userNumber.text!!.isEmpty())

            Toast.makeText(this, "Please fill all inputs!", Toast.LENGTH_SHORT).show()
        else
            storeData()
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        val editor = preferences.edit()
        editor.putString("number",binding.userNumber.text.toString())
       editor.putString("name",binding.userName.text.toString())

        editor.apply()


        val data =  UserModel(userName =  binding.userName.text.toString(), userNumber = binding.userNumber.text.toString())

        Firebase.firestore.collection("users").document(binding.userNumber.text.toString())
            .set(data).addOnSuccessListener {

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error! Please try again", Toast.LENGTH_SHORT).show()
            }
    }
    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}