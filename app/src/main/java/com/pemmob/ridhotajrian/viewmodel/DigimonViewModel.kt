package com.pemmob.ridhotajrian.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.ridhotajrian.model.DigimonDetail
import com.pemmob.ridhotajrian.model.DigimonItem
import com.pemmob.ridhotajrian.repository.DigimonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class DigimonViewModel : ViewModel() {
    private val repository = DigimonRepository()

    // ----------------------------------------------------
    // ----------------------------------------------------
    private val _homeState = MutableStateFlow<UiState<List<DigimonItem>>>(UiState.Loading)
    val homeState: StateFlow<UiState<List<DigimonItem>>> = _homeState

    // ----------------------------------------------------
    // ----------------------------------------------------
    private val _detailState = MutableStateFlow<UiState<DigimonDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<DigimonDetail>> = _detailState

    init {
        fetchDigimons()
    }

    private fun fetchDigimons() {
        viewModelScope.launch {
            _homeState.value = UiState.Loading
            try {
                val response = repository.getDigimons()
                _homeState.value = UiState.Success(response.content)
            } catch (e: Exception) {
                _homeState.value = UiState.Error(e.message ?: "Gagal mengambil data Digimon")
            }
        }
    }


    fun fetchDigimonDetail(name: String) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            try {
                val response = repository.getDigimonDetail(name)
                _detailState.value = UiState.Success(response)
            } catch (e: Exception) {
                _detailState.value = UiState.Error(e.message ?: "Gagal memuat detail Digimon")
            }
        }
    }
}