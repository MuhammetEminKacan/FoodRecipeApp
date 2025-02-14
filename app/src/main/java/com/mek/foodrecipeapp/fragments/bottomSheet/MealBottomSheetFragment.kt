package com.mek.foodrecipeapp.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mek.foodrecipeapp.R
import com.mek.foodrecipeapp.activities.MainActivity
import com.mek.foodrecipeapp.activities.MealActivity
import com.mek.foodrecipeapp.databinding.FragmentMealBottomSheetBinding
import com.mek.foodrecipeapp.fragments.HomeFragment
import com.mek.foodrecipeapp.viewModel.HomeViewModel


private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding : FragmentMealBottomSheetBinding
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            mealId = bundle.getString(MEAL_ID)
            Log.d("MealBottomSheet", "Received Meal ID: $mealId")
        }


        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { meal ->
            viewModel.getMealById(meal)
        }


        observeBottomSheetMeal()
        onBottomSheetDialogClick()

    }


    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName !=null && mealThumb !=null){
                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName : String ?=null
    private var mealThumb : String ?=null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner, Observer { meal ->

            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetMealName.text = meal.strMeal
            binding.tvBottomSheetLocation.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(HomeFragment.MEAL_ID, param1)

                }
            }
    }
}