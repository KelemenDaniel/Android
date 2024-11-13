package com.example.recipeapp.ui.recipe.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.repository.recipe.ViewModel.RecipeListViewModel
import com.example.recipeapp.ui.recipe.adapter.RecipesListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)

        val viewModel =
            ViewModelProvider(this).get(RecipeListViewModel::class.java)

        viewModel.loadInstructionData(requireContext())

        val binding = FragmentRecipeBinding.inflate(inflater, container, false)

        val recyclerView = binding.recipeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            for (recipe in recipes) {
                Log.d("RecipeData", recipe.toString())
            }

            recyclerView.adapter = RecipesListAdapter(recipes)
        }

        viewModel.loadInstructionData(this.requireContext())

        return binding.root
    }

}