package com.example.recipe.ui.global.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.databinding.ItemMainCategoryBinding
import com.example.recipe.di.entity.CategoryItems

class MainCategoryAdapter(val list: ArrayList<CategoryItems>,   val onClick:(str:String)-> Unit) : RecyclerView.Adapter<MainCategoryAdapter.VH>() {

    inner class VH(var binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(categoryItem: CategoryItems) {
            binding.apply {
                Glide
                    .with(itemView.context)
                    .load(categoryItem.strcategorythumb)
                    .centerCrop()
                    .placeholder(R.drawable.cat_img)
                    .into(ivDish)
                tvDishName.text = categoryItem.strcategory

                root.setOnClickListener {
                    onClick(categoryItem.strcategory)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}