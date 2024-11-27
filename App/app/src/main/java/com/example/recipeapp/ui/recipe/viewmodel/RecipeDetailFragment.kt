package com.example.recipeapp.ui.recipe.viewmodel

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.databinding.FragmentRecipeDetailBinding
import com.example.recipeapp.repository.recipe.ViewModel.RecipeDetailViewModel
import com.example.recipeapp.repository.recipe.ViewModel.RecipeListViewModel
import com.example.recipeapp.repository.recipe.model.InstructionModel
import com.example.recipeapp.repository.recipe.model.RecipeModel
import com.example.recipeapp.ui.recipe.adapter.RecipesListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun List<InstructionModel>.toFormattedString(): String {
        return this.sortedBy { it.position } // Ensure instructions are ordered
            .joinToString("\n") { "${it.position}. ${it.displayText}" }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel =
            ViewModelProvider(this).get(RecipeDetailViewModel::class.java)

        viewModel.loadInstructionData(requireContext())
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)


        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            val player = ExoPlayer.Builder(requireContext()).build()
            val videoView = view?.findViewById<PlayerView>(R.id.videoView)
            videoView?.player = player
            val mediaItem = MediaItem.fromUri(recipes.originalVideoUrl.toUri())
            player.setMediaItem(mediaItem)
            player.prepare()
            val image = view?.findViewById<ImageView>(R.id.imageView3)
            if (image != null) {
                Glide.with(requireContext())
                    .load(recipes.thumbnailUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fallback(R.drawable.ic_launcher_background)
                    .into(image)
            }
            val description = view?.findViewById<TextView>(R.id.textView5)
            val title = view?.findViewById<TextView>(R.id.textView4)
            description?.text = recipes.description
            title?.text = recipes.name
            val instructions = view?.findViewById<TextView>(R.id.textView7)
            val formattedInstructions = recipes.instructions.toFormattedString()
            instructions?.text = formattedInstructions
        }

        viewModel.loadInstructionData(this.requireContext())

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.videoView.player?.pause()
    }

}