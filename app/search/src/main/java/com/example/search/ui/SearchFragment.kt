package com.example.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.common.BaseFragment
import com.example.common.visible
import com.example.search.R
import com.example.search.databinding.FragmentSearchBinding
import com.example.search.ui.adapter.RecipeAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment() {

    private val _searchViewModel: SearchViewModel by viewModel()
    private lateinit var _binding: FragmentSearchBinding
    private lateinit var _recipesAdapter: RecipeAdapter

    override fun onStart() {
        super.onStart()
        _searchViewModel.getRecipes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        setView()
        _searchViewModel.uiState.observe(requireActivity(), ::updateUI)
        return _binding.root
    }

    private fun setView() {
        _recipesAdapter = RecipeAdapter(::goToDetail)
        _binding.swipeRefresh.setOnRefreshListener {
            if (_binding.swipeRefresh.isRefreshing) _searchViewModel.getRecipes()
        }
        with(_binding) {
            recyclerViewSearchResults.adapter = _recipesAdapter
            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    print(s)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    print(s)
                }

                override fun afterTextChanged(s: Editable?) {
                    _recipesAdapter.filterRecipes(s.toString())
                }

            })
        }
    }

    private fun updateUI(screenState: SearchUIState) {
        when (screenState) {
            is SearchUIState.Error -> showMessage(screenState.message)
            is SearchUIState.HasRecipesContent -> _recipesAdapter.updateItems(screenState.list)
            is SearchUIState.Loading -> showOrHideLoading(screenState.visible)
            is SearchUIState.OffLineNetwork -> showMessage(getString(R.string.connection_error_description))
            SearchUIState.NotContent -> showEmptyMessage(true)
        }

    }

    private fun showEmptyMessage(isEmpty: Boolean) {
        if (!isEmpty) {
            _binding.recyclerViewSearchResults.visible = true
            _binding.emptyList.root.visible = false
        } else {
            _binding.recyclerViewSearchResults.visible = false
            _binding.emptyList.root.visible = true
        }

    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showOrHideLoading(visible: Boolean) {
        if (_binding.swipeRefresh.isRefreshing) _binding.swipeRefresh.isRefreshing = false
        _binding.progressBar.visible = visible
    }

    private fun goToDetail(uuid: String) {
        val args = Bundle()
        args.putString("uuid", uuid)
        val request = NavDeepLinkRequest.Builder
            .fromUri(getString(R.string.deep_link_search_to_detail, uuid).toUri())
            .build()
        findNavController().navigate(request)
    }

}