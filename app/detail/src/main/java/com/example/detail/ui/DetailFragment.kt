package com.example.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.common.BaseFragment
import com.example.common.visible
import com.example.detail.R
import com.example.detail.databinding.FragmentDetailBinding
import com.example.detail.domain.entity.Location
import com.example.detail.domain.entity.MapInfo
import com.example.detail.domain.entity.RecipeDetail
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment() {

    private val _detailViewModel: DetailViewModel by viewModel()
    private lateinit var _binding: FragmentDetailBinding
    private val _args: DetailFragmentArgs by navArgs()
    private var uuid: String? = null

    override fun onStart() {
        super.onStart()
        uuid?.let { _detailViewModel.getRecipe(it) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uuid = _args.uuid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        _detailViewModel.uiState.observe(requireActivity(),::updateUI)
        _binding.btnGoMap.setOnClickListener{  goToMap()  }
        return _binding.root
    }


    private fun setRecipeDetailView(recipeDetail: RecipeDetail) {
        _binding.recipeName.text = recipeDetail.name
        _binding.recipeDetailText.text = recipeDetail.description
        _binding.recipeRatingBar.rating = recipeDetail.rate

        Glide.with(requireContext())
            .load(recipeDetail.imageUrl)
            .centerCrop()
            .into(_binding.imageView)


    }
    private fun updateUI(screenState: DetailUIState) {
        when (screenState) {
            is DetailUIState.Error -> showMessage(screenState.message)
            is DetailUIState.HasRecipesContent -> setRecipeDetailView(screenState.recipeDetail)
            is DetailUIState.Loading -> showOrHideLoading(screenState.visible)
            is DetailUIState.OffLineNetwork -> showMessage(getString(R.string.connection_error_description))
            DetailUIState.NotContent -> showMessage(getString(R.string.recipe_not_found))
        }

    }
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
    private fun showOrHideLoading(visible: Boolean) {
        _binding.progressBar.visible = visible
    }

    private fun goToMap(){
        _detailViewModel.recipeDetail?.let {
            val actionToMap = DetailFragmentDirections.actionDetailFragmentToMapsFragment(MapInfo(it.name,it.location))
            findNavController().navigate(actionToMap)
        }

    }

}