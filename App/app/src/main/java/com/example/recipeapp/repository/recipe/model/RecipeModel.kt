package com.example.recipeapp.repository.recipe.model

data class RecipeModel(
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val keywords: String,
    val userEmail: String,
    val originalVideoUrl: String,
    val country: String,
    val numServings: Long,
    val components: List<ComponentModel>,
    val instructions: List<InstructionModel>,
    var recipeID: Long
)
