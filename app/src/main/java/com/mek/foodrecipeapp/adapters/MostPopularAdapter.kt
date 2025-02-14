package com.mek.foodrecipeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mek.foodrecipeapp.databinding.PopularItemsBinding
import com.mek.foodrecipeapp.model.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick : ((MealsByCategory) -> Unit)
    var onLongItemClick : ((MealsByCategory) -> Unit) ?= null
    private var mealsList = ArrayList<MealsByCategory>()

    inner class PopularMealViewHolder( var binding : PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)



    fun setMeals(mealsList : ArrayList<MealsByCategory>){
        // bu kısım her yenilendiğinde farklı resimler getirmesi için
        this.mealsList = mealsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val binding =PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PopularMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealsList[position])
            true

        }
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }
}