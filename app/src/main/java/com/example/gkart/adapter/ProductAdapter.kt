package com.example.gkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gkart.activity.ProductDetailsActivity
import com.example.gkart.databinding.LayoutProductItemBinding
import com.example.gkart.model.AddProductModel

class ProductAdapter(private val context: Context, private val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding: LayoutProductItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val  binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImage).into(holder.binding.imageView2)
        holder.binding.textView.text = data.productName
        holder.binding.textView3.text = data.productCategory
        holder.binding.textView4.text = data.productMrp

        holder.binding.button.text = data.productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }

        }
    override fun getItemCount(): Int {
        return list.size
    }
}