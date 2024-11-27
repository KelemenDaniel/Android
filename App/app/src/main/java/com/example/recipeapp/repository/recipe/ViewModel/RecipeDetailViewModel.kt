package com.example.recipeapp.repository.recipe.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.repository.recipe.model.RecipeDetailModel
import kotlinx.coroutines.launch

class RecipeDetailViewModel: ViewModel() {
    private val _recipeList = MutableLiveData<RecipeDetailModel>()
    val recipeList: LiveData<RecipeDetailModel> get() = _recipeList
    private val recipeRepository = RecipeRepository()


    fun loadInstructionData(context: Context) {
        viewModelScope.launch {
            val recipeList = recipeRepository.getDetail(context)
            _recipeList.postValue(recipeList)
        }
    }
}