package com.jhkim.feature.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.BookRepository
import com.jhkim.core.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _bookList: MutableLiveData<List<Book>> = MutableLiveData()
    val bookList: LiveData<List<Book>> = _bookList

    fun search(query: String) = viewModelScope.launch {
        Log.i("jhkim", "test1 start")
        bookRepository.searchBook(query, 1).collect {
            _bookList.postValue(it)
            Log.i("jhkim", it.toString())
        }
        Log.i("jhkim", "test1 end")

    }

}