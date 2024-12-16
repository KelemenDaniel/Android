package com.example.recipeapp

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.repository.recipe.RecipeDao
import com.example.recipeapp.repository.recipe.RecipeDatabase
import com.example.recipeapp.repository.recipe.RecipeRepository
import com.example.recipeapp.service.RecipeApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeApiClient(): RecipeApiClient {
        return RecipeApiClient()
    }
}