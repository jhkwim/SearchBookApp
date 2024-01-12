package com.jhkim.core.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.BookRepository
import com.jhkim.core.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    var isLoading = false

    val bookList: StateFlow<List<Book>> = bookRepository.books.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = listOf())

    init {
        viewModelScope.launch {
            bookRepository.clear()
        }
    }

    fun search(query: String = "") = viewModelScope.launch(Dispatchers.IO) {
        isLoading = true

        runCatching {
            bookRepository.searchBook(query)
        }.onSuccess {
            isLoading = false
        }.onFailure {
            Log.e("jhkim", it.message.toString())
            isLoading = false
        }
    }

}