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
import com.example.recipeapp.service.RecipeApiClient
import com.example.recipeapp.ui.recipe.viewmodel.AlreadyFavouriteException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao, private val recipeApiClient: RecipeApiClient) {
    lateinit var recipeList: MutableList<RecipeModel>
    lateinit var recipeDetailModel: RecipeDetailModel

    fun getAll(context: Context): List<RecipeModel> {
        return readAll(context, "more_recipes.json").toRecipeModelList()
    }

    fun getDetail(context: Context): RecipeDetailModel {
        return readDetail(context, "recipe_details.json").toModel()

    }

    suspend fun getMyRecipesApi(): List<RecipeModel> {
        return recipeApiClient.getMyRecipes()?.toRecipeModelList()?: emptyList()
    }

    suspend fun getAllAPi(): List<RecipeModel>{
        return recipeApiClient.getRecipes()?.toRecipeModelList() ?: emptyList()
    }

    suspend fun getRecipeDetailFromApi(id: Long): RecipeDetailModel?{
        return recipeApiClient.getRecipeDetail(id)?.toModel()
    }

    fun readDetail(context: Context, fileName: String): RecipeDetailDTO {
        val gson = Gson()
        lateinit var recipeList: RecipeDetailDTO
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            val type = object : TypeToken<RecipeDetailDTO>() {}.type
            gson.fromJson<RecipeDetailDTO>(jsonString, type)
            recipeList = gson.fromJson(jsonString, type)
            Log.i("GSON", recipeList.toString())
        } catch (e: JSONException) {
            Log.d("GSON", e.message.toString())
        }
        return recipeList
    }

    fun readAll(context: Context, fileName: String): List<RecipeDTO> {
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
            name = this.name.toString(),
            description = this.description.toString(),
            thumbnailUrl = this.thumbnailUrl.toString(),
            keywords = this.keywords.toString(),
            userEmail = this.userEmail.toString(),
            originalVideoUrl = this.originalVideoUrl.toString(),
            country = this.country.toString(),
            numServings = this.numServings?: 0,
            components = this.components?.toComponentModelList()?: emptyList(),
            instructions = this.instructions?.toInstructionModelList()?: emptyList(),
            recipeID = recipeID?: 0
        )
    }


    private fun RecipeDetailDTO.toModel(): RecipeDetailModel {
        return RecipeDetailModel(
            name = this.name.toString(),
            description = this.description.toString(),
            thumbnailUrl = this.thumbnailUrl.toString(),
            keywords = this.keywords.toString(),
            userEmail = this.userEmail.toString(),
            originalVideoUrl = this.originalVideoUrl.toString(),
            country = this.country.toString(),
            numServings = this.numServings?: 0,
            components = this.components?.toComponentModelList()?: emptyList(),
            instructions = this.instructions?.toInstructionModelList() ?: emptyList(),
            nutrition = this.nutrition?.toModel()?: NutritionModel(0,0,0,0,0,0),
        )
    }

    private fun NutritionDTO.toModel(): NutritionModel {
        return NutritionModel(
            calories = this.calories?: 0,
            protein = this.protein?: 0,
            fat = this.fat?: 0,
            carbohydrates = this.carbohydrates?: 0,
            sugar = this.sugar?: 0,
            fiber = this.fiber?: 0
        )
    }


    private fun MeasurementDTO.toModel(): MeasurementModel {
        return MeasurementModel(
            quantity = this.quantity.toString(), unit = this.unit?.toModel()?: UnitModel("","","","")
        )
    }


    private fun InstructionDTO.toModel(): InstructionModel {
        return InstructionModel(
            position = this.position?: 0, displayText = this.displayText.toString()
        )
    }

    private fun IngredientDTO.toModel(): IngredientModel {
        return IngredientModel(
            name = this.name.toString()
        )
    }

    private fun ComponentDTO.toModel(): ComponentModel {
        return ComponentModel(
            rawText = this.rawText.toString(),
            extraComment = this.extraComment.toString(),
            ingredient = this.ingredient?.toModel()?: IngredientModel(""),
            measurement = this.measurement?.toModel()?: MeasurementModel("", UnitModel("","","","")),
            position = this.position?: 0
        )
    }

    private fun UnitDTO.toModel(): UnitModel {
        return UnitModel(
            name = this.name.toString(),
            displaySingular = this.displaySingular.toString(),
            displayPlural = this.displayPlural.toString(),
            abbreviation = this.abbreviation.toString()
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

    suspend fun insertRecipe(recipe: RecipeEntity): Boolean {
        val recipe1: RecipeEntity? = this.recipeDao.getRecipeById(recipe.internalId)
        Log.d("Reppo", recipe1.toString())
        if (recipe1 == null)
        {
            recipeDao.insertRecipe(recipe)
            return true
        }
        else
            return false
    }

    suspend fun getAllRecipes(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map {
            val jsonObject = JSONObject(it.json)
            val gson = Gson()
            jsonObject.apply { put("id", it.internalId) }
            val recipe = gson.fromJson(jsonObject.toString(), RecipeDTO::class.java).toModel()
            recipe.recipeID = it.internalId
            recipe
        }
    }

    suspend fun deleteRecipe(recipeID: Long) {
        recipeDao.deleteRecipe(recipeDao.getRecipeById(recipeID)!!)
    }

    suspend fun insertRecipeToApi(recipe: RecipeModel) {
        recipeApiClient.createRecipe(
            RecipeDetailDTO(
                recipeId = recipe.recipeID,
                name = recipe.name,
                description = recipe.description,
                thumbnailUrl = recipe.thumbnailUrl,
                keywords = recipe.keywords,
                isPublic = true,
                userEmail = recipe.userEmail,
                originalVideoUrl = recipe.originalVideoUrl,
                country = recipe.country,
                numServings = recipe.numServings,
                components = recipe.components.map { ComponentDTO(
                    rawText = it.rawText,
                    extraComment = it.extraComment,
                    ingredient = IngredientDTO(
                        name = it.ingredient.name
                    ),
                    measurement = MeasurementDTO(
                        quantity = it.measurement.quantity,
                        unit = UnitDTO(
                            name = it.measurement.unit.name,
                            displaySingular = it.measurement.unit.displaySingular,
                            displayPlural = it.measurement.unit.displayPlural,
                            abbreviation = it.measurement.unit.abbreviation
                        )
                    ),
                    position = it.position
                ) },
                instructions = recipe.instructions.map{
                    InstructionDTO(
                        instructionId = null,
                        displayText = it.displayText,
                        position = it.position
                    )
                },
                nutrition = NutritionDTO(0,0,0,0,0,0)
            )
        )
    }

    suspend fun deleteRecipeFromApi(recipeID: Long) {
        recipeApiClient.deleteRecipe(recipeID)
    }

}