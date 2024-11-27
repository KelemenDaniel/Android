package com.example.recipeapp.ui.recipe.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.recipeapp.R


class NewRecipeFragment : Fragment() {

    private lateinit var instructionsContainer: LinearLayout
    private lateinit var ingredientsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)

        instructionsContainer = view.findViewById(R.id.instructionsContainer)
        ingredientsContainer = view.findViewById(R.id.ingredientsContainer)

        addInstruction()
        addIngredient()

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
                addInstruction()
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
                addIngredient()
            }
        }
        ingredientsContainer.addView(editText)
    }

}