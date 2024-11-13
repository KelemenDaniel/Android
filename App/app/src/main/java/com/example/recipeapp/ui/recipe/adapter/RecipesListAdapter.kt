package com.example.recipeapp.ui.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.repository.recipe.model.RecipeModel

class RecipesListAdapter(private val dataSet: List<RecipeModel>, private val context: Context, ) :
    RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView
        val title: TextView
        val image: ImageView

        init {
            description = view.findViewById(R.id.description)
            title = view.findViewById(R.id.title)
            image = view.findViewById(R.id.recipeImage)
        }
    }

    override fun onBindViewHolder(viewHolder: RecipeViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].name
        viewHolder.description.text = dataSet[position].description
        Glide.with(context)
            .load(dataSet[position].thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(viewHolder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_row_item, parent, false)

        return RecipeViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

}