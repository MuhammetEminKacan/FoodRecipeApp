package com.mek.foodrecipeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mek.foodrecipeapp.R
import com.mek.foodrecipeapp.activities.MainActivity
import com.mek.foodrecipeapp.adapters.MealsAdapter
import com.mek.foodrecipeapp.databinding.FragmentSearchBinding
import com.mek.foodrecipeapp.viewModel.HomeViewModel

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var  viewModel : HomeViewModel
    private lateinit var  myadapter : MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener { searchMeals() }

        observeSearchedMealsLiveData()

    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            myadapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val searchQuery =  binding.etSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        myadapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = myadapter
        }
    }
}