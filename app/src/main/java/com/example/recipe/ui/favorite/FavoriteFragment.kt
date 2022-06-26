package com.example.recipe.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipe.R
import com.example.recipe.databinding.FragmentFavoriteBinding
import com.example.recipe.di.RecipeDatabase
import com.example.recipe.di.entity.MealsItems
import com.example.recipe.ui.global.adapter.SubCategoryAdapter
import com.example.recipe.ui.global.base.BaseFragment

class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {

    private var arrSubCategory = ArrayList<MealsItems>()

    private lateinit var subAdapter: SubCategoryAdapter

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }

    private var _bn: FragmentFavoriteBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentFavoriteBinding.bind(view)
        setUpUI()
    }

    override fun setUpUI() {
        bn.apply {
            setAdapter()
        }
    }

    private fun setAdapter() {
        val cat =  RecipeDatabase.getDatabase(requireContext()).recipeDao().getFavorites()

        arrSubCategory = cat as ArrayList<MealsItems>

        subAdapter = SubCategoryAdapter(arrSubCategory){
            navController.navigate(R.id.detailFragment, bundleOf("mealId" to it.idMeal, "category" to it.categoryName, "meal" to it ))
        }

        bn.rvMeals.adapter = subAdapter

    }

}