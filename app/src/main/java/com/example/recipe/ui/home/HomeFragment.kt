package com.example.recipe.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipe.R
import com.example.recipe.databinding.FragmentHomeBinding
import com.example.recipe.di.RecipeDatabase
import com.example.recipe.di.entity.CategoryItems
import com.example.recipe.di.entity.MealsItems
import com.example.recipe.ui.global.adapter.MainCategoryAdapter
import com.example.recipe.ui.global.adapter.SubCategoryAdapter
import com.example.recipe.ui.global.base.BaseFragment
import com.example.recipe.utils.CONSTANTS

class HomFragment : BaseFragment(R.layout.fragment_home) {

    private var arrMainCategory = ArrayList<CategoryItems>()
    private var arrSubCategory = ArrayList<MealsItems>()
    private var arrSearchMeals = ArrayList<MealsItems>()

    private lateinit var subAdapter: SubCategoryAdapter
    private lateinit var mainAdapter: MainCategoryAdapter

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentHomeBinding.bind(view)
        setUpUI()

    }

    override fun setUpUI() {
        bn.apply {
            getDataFromDb()

         //   searchMeals()
        }
    }

//    private fun searchMeals() {
//        bn.apply {
//
//            val cat =  RecipeDatabase.getDatabase(requireContext()).recipeDao().getAllMeal()
//            arrSearchMeals = cat as ArrayList<MealsItems>
//
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    if (arrSearchMeals.contains(query)) {
//                        subAdapter.(arrSearchMeals.filter(
//                        )
//                    } else {
//                        showCustomMessage("No Match found")
//                    }
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//
//                }
//
//            })
//        }
//    }

    private fun getDataFromDb() {
        val cat =  RecipeDatabase.getDatabase(requireContext()).recipeDao().getAllCategory()
        arrMainCategory = cat as ArrayList<CategoryItems>
        arrMainCategory.reverse()

        getMealFromDb(arrMainCategory[0].strcategory)

        mainAdapter = MainCategoryAdapter(arrMainCategory){
            getMealFromDb(it)
        }

        bn.rvMainCategory.adapter = mainAdapter
    }

    private fun getMealFromDb(strCategory: String) {
        val cat =  RecipeDatabase.getDatabase(requireContext()).recipeDao().getSpecificMealList(strCategory)
        arrSubCategory = cat as ArrayList<MealsItems>

        setAdapterMeal(arrSubCategory)
    }

    private fun setAdapterMeal(arrSubCategory: ArrayList<MealsItems>) {
        subAdapter = SubCategoryAdapter(arrSubCategory){
            navController.navigate(R.id.detailFragment, bundleOf( CONSTANTS.MEAL_ITEM to it ))
        }
        bn.rvSubCategory.adapter = subAdapter
    }


}