package com.mek.foodrecipeapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mek.foodrecipeapp.db.MealDatabase
import com.mek.foodrecipeapp.model.Category
import com.mek.foodrecipeapp.model.CategoryList
import com.mek.foodrecipeapp.model.MealsByCategoryList
import com.mek.foodrecipeapp.model.MealsByCategory
import com.mek.foodrecipeapp.model.Meal
import com.mek.foodrecipeapp.model.MealList
import com.mek.foodrecipeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeViewModel(var mealDatabase : MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var searchedMealsLiveData = MutableLiveData<List<Meal>>()

    fun getRandomMeal(){
        RetrofitInstance.myApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if (p1.body() != null){
                    val randomMeal : Meal = p1.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("HomeFragmentEror",p1.message.toString())
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.myApi.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(p0: Call<MealsByCategoryList>, p1: Response<MealsByCategoryList>) {
                if (p1.body() != null){
                    popularItemsLiveData.value = p1.body()!!.meals
                }
            }

            override fun onFailure(p0: Call<MealsByCategoryList>, p1: Throwable) {
                Log.d("home fragment",p1.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.myApi.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(p0: Call<CategoryList>, p1: Response<CategoryList>) {
                p1.body()?.let { categoryList ->
                    Log.e("ibooo","veri geldi")
                    categoriesLiveData.postValue(categoryList.categories)  // üstteki on response un farklı yazım stili
                }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.d("home fragment",p1.message.toString())
            }

        })
    }

    fun getMealById(id : String){
        RetrofitInstance.myApi.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                val meal = p1.body()?.meals?.first()
                meal?.let {

                    bottomSheetMealLiveData.postValue(it)
                }

            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.e("homeViewModel",p1.message.toString())
            }

        })
    }

    fun searchMeals(searchQuery:String) = RetrofitInstance.myApi.searchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                val mealsList = p1.body()?.meals
                mealsList?.let {
                    searchedMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.e("homeViewModelSearch",p1.message.toString())
            }


        })

    fun deleteMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }
    }

    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData() : LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData() : LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData() : LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
    fun observeBottomSheetMealLiveData() : LiveData<Meal>{
       return bottomSheetMealLiveData
    }

    fun observeSearchedMealsLiveData() : LiveData<List<Meal>>{
        return searchedMealsLiveData
    }
}