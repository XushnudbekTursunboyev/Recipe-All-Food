package com.example.recipe.di.dao

import androidx.room.*
import com.example.recipe.di.entity.CategoryItems
import com.example.recipe.di.entity.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
     fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMeal(mealsItems: MealsItems?)

    @Query("DELETE FROM categoryitems")
     fun clearDb()

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
     fun getSpecificMealList(categoryName:String) : List<MealsItems>

     @Update
     fun update(mealsItems: MealsItems)

     @Query("select * from MealItems where favorite")
     fun getFavorites():List<MealsItems>

     @Query("select * from MealItems where idMeal")
     fun getAllMeal():List<MealsItems>
}