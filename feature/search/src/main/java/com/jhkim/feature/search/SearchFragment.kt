package com.jhkim.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.jhkim.feature.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchResultAdapter = SearchResultAdapter()

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.buttonSearch.setOnClickListener { viewModel.search(binding.editQuery.text.toString()) }
        binding.listSearchResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter
        }

        viewModel.bookList.observe(viewLifecycleOwner) {
            searchResultAdapter.submitList(it)
        }

        return binding.root
    }

}