package com.example.recipeapp.repository.recipe.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.recipe.RecipeEntity
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.repository.recipe.model.RecipeDetailModel
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val _recipeList = MutableLiveData<RecipeDetailModel?>()
    val recipeList: MutableLiveData<RecipeDetailModel?> get() = _recipeList


    fun loadInstructionData(id: Long) {
        viewModelScope.launch {
            val recipeList = recipeRepository.getRecipeDetailFromApi(id)
            _recipeList.postValue(recipeList)
        }
    }
}