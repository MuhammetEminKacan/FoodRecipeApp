package com.mek.foodrecipeapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mek.foodrecipeapp.db.MealDatabase

class HomeViewModelFactory(private val mealDatabase : MealDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }

}