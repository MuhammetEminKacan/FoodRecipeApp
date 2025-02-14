package com.mek.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mek.foodrecipeapp.databinding.CategoryItemBinding
import com.mek.foodrecipeapp.model.Category
import com.mek.foodrecipeapp.model.CategoryList

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoriesList  = ArrayList<Category>()
     var onItemClick : ((Category) -> Unit) ?= null

    fun setCategoryList(categoriesList : List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()

    }

    inner class CategoryViewHolder(var binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }

    }


    override fun getItemCount(): Int {
        return categoriesList.size
    }
}