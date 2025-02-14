package com.mek.foodrecipeapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mek.foodrecipeapp.model.MealsByCategory
import com.mek.foodrecipeapp.model.MealsByCategoryList
import com.mek.foodrecipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.myApi.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                p0: Call<MealsByCategoryList>,
                p1: Response<MealsByCategoryList>
            ) {
                p1.body()?.let { mealList ->
                    mealsLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(p0: Call<MealsByCategoryList>, p1: Throwable) {
                Log.d("category meals view model",p1.message.toString())
            }


        })
    }

    fun observeMealsLiveData() : LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }

}