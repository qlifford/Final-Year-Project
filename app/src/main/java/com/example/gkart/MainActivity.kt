package com.example.gkart

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.gkart.activity.LoginActivity
import com.example.gkart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()

        if (FirebaseAuth.getInstance().currentUser == null){
           startActivity(Intent(this, LoginActivity::class.java))
            finish()
      }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    i = 0;navController.navigate(R.id.homeFragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
        }

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.cartFragment -> "My Cart"
                    R.id.moreFragment -> "Dashboard"
                    else -> "Home"
                }
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (i == 0){
            finish()
        }
    }
}