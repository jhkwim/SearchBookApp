package com.jhkim.core.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhkim.feature.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val REQUEST_KEY_SEARCH = "request_search"
const val RESULT_kEY_ISBN3 = "result_isbn3"

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchResultAdapter = SearchResultAdapter {
            setFragmentResult(REQUEST_KEY_SEARCH, bundleOf(RESULT_kEY_ISBN3 to it.isbn13))
        }

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.buttonSearch.setOnClickListener { viewModel.search(binding.editQuery.text.toString()) }
        binding.listSearchResult.apply {
            val layoutManager = LinearLayoutManager(context).also {
                this@apply.layoutManager = it
            }
            adapter = searchResultAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition =layoutManager.findFirstVisibleItemPosition()

                    // 스크롤이 맨 아래로 내려왔을 때 추가 데이터 로딩

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (!viewModel.isLoading) {
                            viewModel.search()
                        }
                    }
                }
            })
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookList.collect {
                    searchResultAdapter.submitList(it)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listSearchResult.adapter = null
        clearFragmentResult(REQUEST_KEY_SEARCH)
    }

}