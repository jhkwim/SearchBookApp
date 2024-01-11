package com.jhkim.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.BookRepository
import com.jhkim.core.model.BookDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ISBN10 = "isbn13"

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val isbn10: String? = stateHandle[ISBN10]

    private val _bookDetail: MutableLiveData<BookDetail> =  MutableLiveData<BookDetail>()
    val bookDetail: LiveData<BookDetail> =  _bookDetail

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (isbn10.isNullOrBlank())
            // TODO: error
                return@launch
            else
                getDetail(isbn10)
        }
    }

    private suspend fun getDetail(isbn10: String) {
        bookRepository.getBookDetail(isbn10).collect {
            _bookDetail.postValue(it)
        }
    }

}