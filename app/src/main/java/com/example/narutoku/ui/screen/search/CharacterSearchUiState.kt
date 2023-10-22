package com.example.narutoku.ui.screen.search

import com.example.narutoku.model.SearchCharacter

sealed interface CharacterSearchUiState {
    object Loading : CharacterSearchUiState
    data class Success(
        val searchResults: SearchCharacter,
        val queryHasNoResults: Boolean
    ) : CharacterSearchUiState

    data class Error(val message: String?) : CharacterSearchUiState
}