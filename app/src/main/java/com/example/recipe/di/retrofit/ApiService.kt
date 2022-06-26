package com.example.recipe.di.retrofit

import com.example.recipe.di.entity.Category
import com.example.recipe.di.entity.Meal
import com.example.recipe.di.entity.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String): Call<MealResponse>


}