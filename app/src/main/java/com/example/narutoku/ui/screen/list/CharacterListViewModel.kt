package com.example.narutoku.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoku.common.Result
import com.example.narutoku.domain.GetCharactersUseCase
import com.example.narutoku.domain.GetFavoriteCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        initializeUiState()
    }

    fun initializeUiState() {
        _uiState.update { CharacterListUiState.Loading }

        val charactersFlow = getCharactersUseCase()
        val favoriteCharactersFlow = getFavoriteCharactersUseCase()

        combine(
            charactersFlow,
            favoriteCharactersFlow
        ) { charactersResult, favoriteCharactersResult ->
            when {
                charactersResult is Result.Error -> {
                    _uiState.update { CharacterListUiState.Error(charactersResult.message) }
                }
                favoriteCharactersResult is Result.Error -> {
                    _uiState.update { CharacterListUiState.Error(favoriteCharactersResult.message) }
                }

                charactersResult is Result.Success && favoriteCharactersResult is Result.Success -> {
                    val characters = charactersResult.data.toImmutableList()
                    val favoriteCharacters = favoriteCharactersResult.data.toImmutableList()

                    _uiState.update {
                        CharacterListUiState.Success(
                            characters = characters,
                            favoriteCharacters = favoriteCharacters
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}