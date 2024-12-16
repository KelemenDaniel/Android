package com.example.recipeapp.ui.recipe.viewmodel

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var recipeList: List<RecipeModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        val bundle = Bundle()
        bundle.putLong("id", recipe.recipeID)
        findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment2, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use binding to inflate the layout
        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        // ViewModel setup

        viewModel.loadInstructionData()

        // RecyclerView setup
        val recyclerView = binding.recipeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            recipeList = recipes
            for (recipe in recipes) {
                Log.d("RecipeData", recipe.toString())
            }

            recyclerView.adapter = RecipesListAdapter(
                recipes.toMutableList(),
                requireContext(),
                onItemClick = { recipe: RecipeModel -> navigateToRecipeDetail(recipe) },
                onItemDelete = { recipe -> true },
                onFavouriteClick = { recipe: RecipeModel ->
                    viewModel.insertFavourite(recipe).observe(viewLifecycleOwner){
                        if (it)
                            Toast.makeText(requireContext(), "Added to favourites!", Toast.LENGTH_SHORT)
                                .show()
                        else
                            Toast.makeText(requireContext(), "Already added to favourites!", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            )
        }

        // FAB click listener
        binding.fab.setOnClickListener {
            Log.d("FloatingActionButton", "Clicked")
            findNavController().navigate(R.id.action_recipeFragment_to_newRecipeFragment2)
        }

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->

            // Handle the action (e.g., process input)
            onSearchButtonClick()
            // Hide the keyboard
            val imm = getSystemService(
                requireContext(),
                InputMethodManager::class.java
            ) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchBar.windowToken, 0)

            // Optionally clear the focus
            binding.searchBar.clearFocus()

            true // Indicate the action was handled

            // Let the system handle other actions

        }

        return binding.root
    }
    private fun onSearchButtonClick() {
        val searchInput = binding.searchBar.text.toString()
        if (searchInput.isNotEmpty()) {
            val recipesFiltered = recipeList.filter { recipe ->
                recipe.name.contains(searchInput, ignoreCase = true)
                        || recipe.keywords.contains(searchInput, ignoreCase = true)
            }
            val adapter: RecipesListAdapter = binding.recipeRecyclerView.adapter as RecipesListAdapter
            adapter.updateRecipes(recipesFiltered)
        } else viewModel.loadInstructionData()
    }

}