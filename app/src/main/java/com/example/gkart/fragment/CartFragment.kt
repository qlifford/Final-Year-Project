package com.example.gkart.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gkart.activity.AddressActivity
import com.example.gkart.activity.CategoryActivity
import com.example.gkart.activity.CheckoutActivity
import com.example.gkart.adapter.CartAdapter
import com.example.gkart.databinding.FragmentCartBinding
import com.example.gkart.roomdb.AppDatabase
import com.example.gkart.roomdb.ProductModel


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for (data in it){
                list.add(data.productId)
            }
            totalCost(it)

        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        list.clear()
        var total = 0
        for (item in data!!) {
           total += item.productSp!!.toInt()

            binding.textView12.text = "Total items in cart : ${data.size}"
            binding.textView13.text = "Total Cost : $total"



            binding.checkout.setOnClickListener {
                val intent = Intent(context, AddressActivity::class.java)
               intent.putExtra("totalCost", total.toString())
               intent.putStringArrayListExtra("productIds", list)
               startActivity(intent)

            }
        }
    }

}
