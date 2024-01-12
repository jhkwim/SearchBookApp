package com.jhkim.core.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.core.data.repository.BookRepository
import com.jhkim.core.model.BookDetail
import com.jhkim.feature.detail.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ISBN13 = "isbn13"

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val isbn13: String? = stateHandle[ISBN13]

    private val _detailState: MutableLiveData<DetailState> = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> =  _detailState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (isbn13.isNullOrBlank()) {
                Log.e("jhkim", "invalid isbn13")
                _detailState.postValue(DetailState.Error)
            } else {
                getDetail(isbn13)
            }
        }
    }

    private suspend fun getDetail(isbn13: String) {
        bookRepository.getBookDetail(isbn13).catch {
            Log.e("jhkim", it.message.toString())
            _detailState.postValue(DetailState.Error)
        }.collect {
            _detailState.postValue(DetailState.Success(it))
        }
    }

}

internal sealed class DetailState {

    object None : DetailState()

    object Error : DetailState()

    data class Success(val bookDetail: BookDetail) : DetailState()

}