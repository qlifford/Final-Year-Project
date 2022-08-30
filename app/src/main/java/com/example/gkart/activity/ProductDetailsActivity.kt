package com.example.gkart.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.gkart.MainActivity
import com.example.gkart.databinding.ActivityProductDetailsBinding
import com.example.gkart.roomdb.AppDatabase
import com.example.gkart.roomdb.ProductDao
import com.example.gkart.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Product Details"

        getProductDetails(intent.getStringExtra("id"))

    }
    private fun getProductDetails(proId: String?) {

        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSp = it.getString("productSp")
                val productDesc = it.getString("productDescription")
                  binding.textView7.text = name
                  binding.textView8.text = productSp
                  binding.textView9.text = productDesc


                val slideList = ArrayList<SlideModel>()
                for (data in list) {
                    slideList.add(SlideModel(data, ScaleTypes.FIT))

                }

                cartAction(
                    proId = proId,
                    name = name,
                    productSp = productSp,
                    coverImage = it.getString("productCoverImage")
                )

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {
                Toast.makeText(this, "Error! Try again", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cartAction(proId: String, name: String?, productSp: String?, coverImage: String?) {
        val productDao = AppDatabase.getInstance(this).productDao()

        if (productDao.isExit(proId) != null) {
            binding.textView10.text = "Go To Cart"
        }else{
            binding.textView10.text = "Add To Cart"

        }
        binding.textView10.setOnClickListener{
            if (productDao.isExit(proId) != null) {

                openCart()

            }else{
              addToCart(productDao, proId,name, productSp, coverImage)

            }
        }
    }

    private fun addToCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverImage: String?
    ) {
        val data = ProductModel(proId, name, coverImage, productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView10.text = "Go To Cart"

        }
    }
    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}


