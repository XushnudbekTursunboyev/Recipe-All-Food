package com.example.recipe.ui.global.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.databinding.ItemSubCategoryBinding
import com.example.recipe.di.entity.MealsItems

class SubCategoryAdapter(val list: List<MealsItems>, val onClick:(mealsItem:MealsItems)->Unit) : RecyclerView.Adapter<SubCategoryAdapter.VH>() {

    inner class VH(var binding: ItemSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mealsItem: MealsItems) {
            binding.apply {
                Glide
                    .with(itemView.context)
                    .load(mealsItem.strMealThumb)
                    .centerCrop()
                    .placeholder(R.drawable.cat_img)
                    .into(ivDish)

                tvDishName.text = mealsItem.strMeal


                root.setOnClickListener {
                    onClick(mealsItem)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}