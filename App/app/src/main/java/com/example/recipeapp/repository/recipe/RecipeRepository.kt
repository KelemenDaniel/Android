package com.example.recipeapp.repository.recipe

import android.content.Context
import android.util.Log
import com.example.recipeapp.repository.recipe.dto.ComponentDTO
import com.example.recipeapp.repository.recipe.dto.IngredientDTO
import com.example.recipeapp.repository.recipe.dto.InstructionDTO
import com.example.recipeapp.repository.recipe.dto.MeasurementDTO
import com.example.recipeapp.repository.recipe.dto.RecipeDTO
import com.example.recipeapp.repository.recipe.dto.RecipeDetailDTO
import com.example.recipeapp.repository.recipe.dto.NutritionDTO
import com.example.recipeapp.repository.recipe.dto.UnitDTO
import com.example.recipeapp.repository.recipe.model.ComponentModel
import com.example.recipeapp.repository.recipe.model.IngredientModel
import com.example.recipeapp.repository.recipe.model.InstructionModel
import com.example.recipeapp.repository.recipe.model.MeasurementModel
import com.example.recipeapp.repository.recipe.model.NutritionModel
import com.example.recipeapp.repository.recipe.model.RecipeDetailModel
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.example.recipeapp.repository.recipe.model.UnitModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException



class RecipeRepository {
    lateinit var recipeList: MutableList<RecipeModel>
    lateinit var recipeDetailModel: RecipeDetailModel

    fun getAll(context: Context): List<RecipeModel> {
        return readAll(context, "more_recipes.json").toRecipeModelList()
    }
    fun readAll(context : Context, fileName : String): List<RecipeDTO> {
        val gson = Gson()
        var recipeList = listOf<RecipeDTO>()
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(jsonString)
            val type = object : TypeToken<List<RecipeDTO>>() {}.type
            gson.fromJson<List<RecipeDTO>>(jsonString, type)
            recipeList = gson.fromJson(jsonArray.toString(), type)
            Log.i("GSON", recipeList.toString())
        } catch (e: JSONException) {
            Log.d("GSON", e.message.toString())
        }
        return recipeList
    }

    private fun RecipeDTO.toModel(): RecipeModel {
        return RecipeModel(
            name = this.name,
            description = this.description,
            thumbnailUrl = this.thumbnailUrl,
            keywords = this.keywords,
            userEmail = this.userEmail,
            originalVideoUrl = this.originalVideoUrl,
            country = this.country,
            numServings = this.numServings,
            components = this.components.toComponentModelList(),
            instructions = this.instructions.toInstructionModelList(),
        )
    }



    private fun RecipeDetailDTO.toModel(): RecipeDetailModel {
        return RecipeDetailModel(
            name = this.name,
            description = this.description,
            thumbnailUrl = this.thumbnailUrl,
            keywords = this.keywords,
            userEmail = this.userEmail,
            originalVideoUrl = this.originalVideoUrl,
            country = this.country,
            numServings = this.numServings,
            components = this.components.toComponentModelList(),
            instructions = this.instructions.toInstructionModelList(),
            nutrition = this.nutrition.toModel(),
        )
    }

    private fun NutritionDTO.toModel(): NutritionModel {
        return NutritionModel(
            calories = this.calories,
            protein = this.protein,
            fat = this.fat,
            carbohydrates = this.carbohydrates,
            sugar = this.sugar,
            fiber = this.fiber
        )
    }



    private fun MeasurementDTO.toModel(): MeasurementModel {
        return MeasurementModel(
            quantity = this.quantity, unit = this.unit.toModel()
        )
    }



    private fun InstructionDTO.toModel(): InstructionModel {
        return InstructionModel(
            position = this.position, displayText = this.displayText
        )
    }

    private fun IngredientDTO.toModel(): IngredientModel {
        return IngredientModel(
            name = this.name
        )
    }

    private fun ComponentDTO.toModel(): ComponentModel {
        return ComponentModel(
            rawText = this.rawText,
            extraComment = this.extraComment,
            ingredient = this.ingredient.toModel(),
            measurement = this.measurement.toModel(),
            position = this.position
        )
    }

    private fun UnitDTO.toModel(): UnitModel {
        return UnitModel(
            name = this.name,
            displaySingular = this.displaySingular,
            displayPlural = this.displayPlural,
            abbreviation = this.abbreviation
        )
    }

    fun List<ComponentDTO>.toComponentModelList(): List<ComponentModel> {
        return this.map { it.toModel() }
    }

    fun List<InstructionDTO>.toInstructionModelList(): List<InstructionModel> {
        return this.map { it.toModel() }
    }

    fun List<MeasurementDTO>.toMeasurementModelList(): List<MeasurementModel> {
        return this.map { it.toModel() }
    }

    fun List<NutritionDTO>.toNutritionModelList(): List<NutritionModel> {
        return this.map { it.toModel() }
    }

    fun List<RecipeDTO>.toRecipeModelList(): List<RecipeModel> {
        return this.map { it.toModel() }
    }


}