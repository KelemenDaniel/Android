package com.example.recipeapp.ui.recipe.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.repository.recipe.ViewModel.RecipeListViewModel
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.example.recipeapp.ui.recipe.adapter.RecipesListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use binding to inflate the layout
        val binding = FragmentRecipeBinding.inflate(inflater, container, false)

        // ViewModel setup

        viewModel.loadInstructionData(requireContext())

        // RecyclerView setup
        val recyclerView = binding.recipeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            for (recipe in recipes) {
                Log.d("RecipeData", recipe.toString())
            }

            recyclerView.adapter = RecipesListAdapter(
                recipes,
                requireContext(),
                onItemClick = { recipe: RecipeModel -> navigateToRecipeDetail(recipe) }
            )
        }

        // FAB click listener
        binding.fab.setOnClickListener {
            Log.d("FloatingActionButton", "Clicked")
            findNavController().navigate(R.id.action_recipeFragment_to_newRecipeFragment2)
        }

        return binding.root
    }


}