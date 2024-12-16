package com.example.recipeapp.repository.recipe.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.recipe.RecipeEntity
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.example.recipeapp.ui.recipe.viewmodel.AlreadyFavouriteException
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    private val _favouriteList = MutableLiveData<List<RecipeModel>>()
    private val _recipeList = MutableLiveData<List<RecipeModel>>()
    val recipeList: LiveData<List<RecipeModel>> get() = _recipeList
    val favouriteList: LiveData<List<RecipeModel>> get() = _favouriteList


    fun insertRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            repository.insertRecipeToApi(recipe)
        }
    }

    fun insertFavourite(recipe: RecipeModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.postValue(
                repository.insertRecipe(
                    RecipeEntity(
                        internalId = recipe.recipeID,
                        json = Gson().toJson(recipe)
                    )
                )
            )
        }
        return result
    }

    fun loadInstructionData() {
        viewModelScope.launch {
            val recipeList = repository.getMyRecipesApi()
            _recipeList.postValue(recipeList)
            val favouriteList = repository.getAllRecipes()
            _favouriteList.postValue(favouriteList)

        }
    }

    fun deleteRecipe(recipeID: Long): Boolean {
        viewModelScope.launch { repository.deleteRecipeFromApi(recipeID) }
        return true
    }

    fun deleteRecipeFromFavourites(recipeID: Long): Boolean {
        viewModelScope.launch { repository.deleteRecipe(recipeID) }
        return true
    }
}