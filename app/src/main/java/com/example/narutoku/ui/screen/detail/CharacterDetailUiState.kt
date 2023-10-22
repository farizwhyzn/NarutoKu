package com.example.narutoku.ui.screen.detail

import com.example.narutoku.model.CharacterDetail

sealed interface CharacterDetailUiState {
    object Loading : CharacterDetailUiState
    data class Success(
        val characterDetail: CharacterDetail,
        val isCharacterFavorite: Boolean
    ) : CharacterDetailUiState

    data class Error(val message: String?) : CharacterDetailUiState
}