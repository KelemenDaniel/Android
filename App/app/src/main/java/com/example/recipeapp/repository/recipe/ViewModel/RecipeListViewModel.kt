package com.example.recipeapp.repository.recipe.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.repository.recipe.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val recipeRepository: RecipeRepository): ViewModel() {
    private val _recipeList = MutableLiveData<List<RecipeModel>>()
    val recipeList: LiveData<List<RecipeModel>> get() = _recipeList


    fun loadInstructionData(context: Context) {
        viewModelScope.launch {
            val recipeList = recipeRepository.getAll(context)
            _recipeList.postValue(recipeList)
        }
    }

}