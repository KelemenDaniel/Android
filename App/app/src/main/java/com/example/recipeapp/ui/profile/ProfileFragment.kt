package com.example.recipeapp.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentProfileBinding
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.repository.recipe.ViewModel.ProfileViewModel
import com.example.recipeapp.repository.recipe.ViewModel.RecipeListViewModel
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.example.recipeapp.ui.recipe.adapter.RecipesListAdapter
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.loadInstructionData()
        val recyclerView = binding.profileRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.recipeList.observe(viewLifecycleOwner){
            recipeList ->
            recyclerView.adapter = RecipesListAdapter(
                dataSet = recipeList.toMutableList(),
                context = requireContext(),
                onItemClick = {},
                onItemDelete = {recipe:RecipeModel ->
                    viewModel.deleteRecipe(recipe.recipeID)
                }
            )
        }

        return binding.root
    }


}