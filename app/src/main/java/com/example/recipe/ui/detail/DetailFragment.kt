package com.example.recipe.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.databinding.FragmentDetailBinding
import com.example.recipe.di.RecipeDatabase
import com.example.recipe.di.api.ApiBuilder
import com.example.recipe.di.entity.MealResponse
import com.example.recipe.di.entity.MealsItems
import com.example.recipe.di.retrofit.ApiService
import com.example.recipe.ui.global.base.BaseFragment
import com.example.recipe.utils.CONSTANTS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private var idMeal = ""
    private var checkFavorites = false
    private lateinit var mealsItems:MealsItems
    private var categoryName = ""
    private var youtubeLink = ""
    private var _bn: FragmentDetailBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mealsItems = getSerializable(CONSTANTS.MEAL_ITEM) as MealsItems
            checkFavorites = mealsItems.checkFavorite
            idMeal = mealsItems.idMeal
            categoryName  =  mealsItems.categoryName

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentDetailBinding.bind(view)


        setUpUI()

        bn.imgToolbarBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        bn.btnYoutube.setOnClickListener {
            val uri = Uri.parse(youtubeLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        bn.apply {
            bn.imgToolbarBtnFav.setImageResource(if (checkFavorites) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_fav_unfill)
        }

        bn.imgToolbarBtnFav.setOnClickListener {

            if (checkFavorites) {
                mealsItems.checkFavorite = false
                bn.imgToolbarBtnFav.setImageResource(R.drawable.ic_fav_unfill)
            } else {
                mealsItems.checkFavorite = true
                bn.imgToolbarBtnFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            checkFavorites = mealsItems.checkFavorite

            RecipeDatabase.getDatabase(requireContext()).recipeDao().update(mealsItems)
        }
    }

    override fun setUpUI() {
        bn.apply {
            getSpecificItem(idMeal)
        }
    }



    private fun getSpecificItem(id:String) {
        val service = ApiBuilder.retrofitInstance!!.create(ApiService::class.java)
        val call = service.getSpecificItem(id)
        call.enqueue(object : Callback<MealResponse> {
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {

                showCustomMessage("Something went wrong")
            }

            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {
                bn.apply {
                    Glide.with(requireActivity()).load(response.body()!!.mealsEntity[0].strmealthumb).into(imgItem)

                    tvCategory.text = response.body()!!.mealsEntity[0].strmeal

                    val ingredient = "${response.body()!!.mealsEntity[0].stringredient1}      ${response.body()!!.mealsEntity[0].strmeasure1}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient2}      ${response.body()!!.mealsEntity[0].strmeasure2}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient3}      ${response.body()!!.mealsEntity[0].strmeasure3}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient4}      ${response.body()!!.mealsEntity[0].strmeasure4}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient5}      ${response.body()!!.mealsEntity[0].strmeasure5}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient6}      ${response.body()!!.mealsEntity[0].strmeasure6}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient7}      ${response.body()!!.mealsEntity[0].strmeasure7}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient8}      ${response.body()!!.mealsEntity[0].strmeasure8}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient9}      ${response.body()!!.mealsEntity[0].strmeasure9}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient10}      ${response.body()!!.mealsEntity[0].strmeasure10}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient11}      ${response.body()!!.mealsEntity[0].strmeasure11}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient12}      ${response.body()!!.mealsEntity[0].strmeasure12}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient13}      ${response.body()!!.mealsEntity[0].strmeasure13}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient14}      ${response.body()!!.mealsEntity[0].strmeasure14}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient15}      ${response.body()!!.mealsEntity[0].strmeasure15}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient16}      ${response.body()!!.mealsEntity[0].strmeasure16}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient17}      ${response.body()!!.mealsEntity[0].strmeasure17}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient18}      ${response.body()!!.mealsEntity[0].strmeasure18}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient19}      ${response.body()!!.mealsEntity[0].strmeasure19}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient20}      ${response.body()!!.mealsEntity[0].strmeasure20}\n"

                    tvIngredients.text = ingredient
                    tvInstructions.text = response.body()!!.mealsEntity[0].strinstructions

                    youtubeLink = response.body()!!.mealsEntity[0].strsource
                }
            }
        })
    }

}