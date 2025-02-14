package com.mek.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mek.foodrecipeapp.databinding.ActivityCategoryMealsBinding
import com.mek.foodrecipeapp.databinding.MealItemBinding

import com.mek.foodrecipeapp.model.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()

    inner class CategoryMealsViewHolder(var binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setMealsList(mealsList : List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryMealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}