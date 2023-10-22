package com.example.narutoku.ui.screen.list

import com.example.narutoku.model.Character
import kotlinx.collections.immutable.ImmutableList

sealed interface CharacterListUiState {
    object Loading : CharacterListUiState
    data class Success(
        val characters: ImmutableList<Character>,
        val favoriteCharacters: ImmutableList<Character>
    ) : CharacterListUiState

    data class Error(val message: String?) : CharacterListUiState
}