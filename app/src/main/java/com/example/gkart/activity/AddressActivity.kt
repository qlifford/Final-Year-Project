package com.example.gkart.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.gkart.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var actionBar: ActionBar
    private lateinit var preferences :  SharedPreferences
    private lateinit var totalCoast: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Address"

  preferences = this.getSharedPreferences("user", MODE_PRIVATE)

       totalCoast = intent.getStringExtra("totalCost")!!
        loadUserInfo()
        
         binding.proceed.setOnClickListener {
             validateData(
                 binding.userNumber.text.toString(),
                 binding.userName.text.toString(),
                 binding.userPinCode.text.toString(),
                 binding.userCity.text.toString(),
                 binding.userState.text.toString(),
                 binding.userVillage.text.toString()
             )
         }
    }

    private fun validateData(number: String, name: String, pinCode: String, city: String, state: String, village: String) {

        if (number.isEmpty() || state.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            storeData(pinCode,state,village,city)
        }

    }

    private fun storeData(pinCode: String, state: String, village: String, city: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCoast)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error! Please try again", Toast.LENGTH_SHORT).show() }

    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userNumber"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userVillage.setText(it.getString("village"))
                binding.userPinCode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener {

            }
    }
}


       /* preferences = this.getSharedPreferences("user", MODE_PRIVATE)


        binding.proceed.setOnClickListener {

            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userpinCode.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userVillage.text.toString())
        }
    }

    private fun validateData(
        number: String,
        name: String,
        pinCode: String,
        city: String,
        state: String,
        village: String
    ) {
        if (number.isEmpty() || name.isEmpty() || city.isEmpty()) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

        } else {

            storeData(pinCode, city, state, village)
        }
    }

    private fun storeData(pinCode: String, city: String, state: String, village: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {

                startActivity(Intent(this, CheckoutActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error! Please try again", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userNumber"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userVillage.setText(it.getString("village"))
                binding.userpinCode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener {

            }
    }
}*/