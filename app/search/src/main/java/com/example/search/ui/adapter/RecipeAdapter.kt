package com.example.search.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search.R
import com.example.search.databinding.RecipeListItemBinding
import com.example.search.domain.entity.Recipe

class RecipeAdapter(
    val showDetailAction: (uuid: String) -> Unit
) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    private var filter: String = ""
    private var recipes: MutableList<Recipe> = mutableListOf()


    private fun getFilteredList(counters: List<Recipe>, filter: String): List<Recipe> =
        counters.filter { it.name.contains(filter, true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getFilteredList(recipes, filter)[position]

        holder.binder.recipeName.text = recipe.name
        Glide.with(holder.binder.imageView.context)
            .load(recipe.imageUrl)
            .centerCrop()
            .into(holder.binder.imageView)

        holder.binder.recipeButtonDetail.setOnClickListener {
            it.isEnabled = false
            showDetailAction(recipe.uuid)
        }
    }

    override fun getItemCount(): Int = getFilteredList(recipes, filter).size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(users: List<Recipe>) {
        this.recipes = users.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterRecipes(filterText: String) {
        filter = filterText
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binder = RecipeListItemBinding.bind(view)
    }
}