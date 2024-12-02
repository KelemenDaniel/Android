package com.example.recipeapp.ui.recipe.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.repository.recipe.RecipeEntity
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.repository.recipe.ViewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class NewRecipeFragment : Fragment() {

    private lateinit var instructionsContainer: LinearLayout
    private lateinit var ingredientsContainer: LinearLayout
    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)
        val parentLayout1 = view.findViewById<ViewGroup>(R.id.parentLayout)
        parentLayout1.setOnTouchListener { _, _ ->
            view.findFocus()?.clearFocus()

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            true
        }
        instructionsContainer = view.findViewById(R.id.instructionsContainer)
        ingredientsContainer = view.findViewById(R.id.ingredientsContainer)

        addInstruction()
        addIngredient()

        val button = view?.findViewById<Button>(R.id.button3)
        if (button != null) {
            button.setOnClickListener {
                val jsonString = saveRecipe()
                viewModel.insertRecipe(RecipeEntity(json = jsonString))
                Log.d("RecipeData", jsonString)
                Toast.makeText(requireContext(), "Recipe saved successfully!", Toast.LENGTH_SHORT).show()

                // Navigate back to the Recipe Fragment
                findNavController().navigateUp()
            }
        }
        return view
    }

    private fun addInstruction() {
        val editText = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            hint = "Enter instruction"
            textSize = 16f
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editText.text.isNotBlank()) {
                // Add a new field only if it's the last field
                val isLastField = instructionsContainer.indexOfChild(editText) == instructionsContainer.childCount - 1
                if (isLastField) {
                    addInstruction()
                }
            }
        }
        instructionsContainer.addView(editText)
    }

    private fun addIngredient() {
        val editText = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            hint = "Enter ingredient"
            textSize = 16f
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && editText.text.isNotBlank()) {
                // Check if this is the last field in the container
                val isLastField = ingredientsContainer.indexOfChild(editText) == ingredientsContainer.childCount - 1
                if (isLastField) {
                    addIngredient()
                }
            }
        }
        ingredientsContainer.addView(editText)
    }


    private fun saveRecipe(): String {
        val recipeName = view?.findViewById<EditText>(R.id.editTextText3)?.text.toString()
        val recipeDescription = view?.findViewById<EditText>(R.id.editTextText6)?.text.toString()
        val recipePictureUrl = view?.findViewById<EditText>(R.id.editTextText7)?.text.toString()
        val recipeVideoUrl = view?.findViewById<EditText>(R.id.editTextText8)?.text.toString()
        val recipeIngredients = ingredientsContainer.children.map { it as EditText }
            .map { it.text.toString() }
            .toList()

        val recipeInstructions = instructionsContainer.children.map { it as EditText }
            .map { it.text.toString() }
            .toList()

        val recipeJson = JSONObject().apply {
            put("name", recipeName)
            put("description", recipeDescription)
            put("pictureUrl", recipePictureUrl)
            put("videoUrl", recipeVideoUrl)
            put("ingredients", JSONArray(recipeIngredients))
            put("instructions", JSONArray(recipeInstructions))
        }

        val jsonString = recipeJson.toString()
        return jsonString
    }

}