package com.example.recipeapp.ui.recipe.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.RecipeRowItemBinding
import com.example.recipeapp.repository.recipe.model.RecipeModel

class RecipesListAdapter(
    private var dataSet: MutableList<RecipeModel>,
    private val context: Context,
    private val onItemClick: (RecipeModel) -> Unit,
    private val onItemDelete: (RecipeModel) -> Boolean,
    private val onFavouriteClick: (RecipeModel) -> Unit

) : RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView
        val title: TextView
        val image: ImageView
        val binding: RecipeRowItemBinding
        init {
            description = view.findViewById(R.id.description)
            title = view.findViewById(R.id.title)
            image = view.findViewById(R.id.recipeImage)
            binding = RecipeRowItemBinding.bind(view)
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
        viewHolder.binding.root.setOnClickListener {
            Log.d("position",this.dataSet[position].recipeID.toString())
            onItemClick(this.dataSet[position])
        }
        viewHolder.binding.root.setOnLongClickListener{
            showDeleteConfirmationDialog(
                context = context,
                position = position
            )
            true
        }
        viewHolder.binding.imageButton.setOnClickListener{
            onFavouriteClick(this.dataSet[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_row_item, parent, false)

        return RecipeViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { _, _ ->
                onItemDelete(dataSet[position])
                dataSet.removeAt(position)
                notifyItemRemoved(position)
            }
            .setNegativeButton("No", null)
            .show()
    }
    fun updateRecipes(recipes: List<RecipeModel>) {
        this.dataSet = recipes.toMutableList()
        this.notifyDataSetChanged()
    }
}