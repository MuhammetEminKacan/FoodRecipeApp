package com.mek.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mek.foodrecipeapp.databinding.MealItemBinding
import com.mek.foodrecipeapp.model.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesMealsAdapterViewHolder>() {

    inner class FavoritesMealsAdapterViewHolder(val binding:MealItemBinding) :RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        return FavoritesMealsAdapterViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.binding.tvMealName.text = meal.strMeal
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
    }
}