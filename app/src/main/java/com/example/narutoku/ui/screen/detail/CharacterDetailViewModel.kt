package com.example.narutoku.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoku.common.Constants.PARAM_CHARACTER_ID
import com.example.narutoku.domain.DeleteFavoriteCharacterUseCase
import com.example.narutoku.domain.GetCharacterDetailUseCase
import com.example.narutoku.domain.InsertFavoriteCharacterUseCase
import com.example.narutoku.domain.IsCharacterFavoriteUseCase
import com.example.narutoku.common.Result
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val isCharacterFavoriteUseCase: IsCharacterFavoriteUseCase,
    private val insertFavoriteCharacterUseCase: InsertFavoriteCharacterUseCase,
    private val deleteFavoriteCharacterUseCase: DeleteFavoriteCharacterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val characterId = savedStateHandle.get<String>(PARAM_CHARACTER_ID)

    init {
        initializeUiState()
    }

    fun initializeUiState() {
        _uiState.update { CharacterDetailUiState.Loading }

        if (characterId == null) {
            _uiState.update { CharacterDetailUiState.Error("Invalid character ID") }
            return
        }

        val characterDetailFlow = getCharacterDetailUseCase(characterId = characterId)
        val isCharacterFavoriteFlow = isCharacterFavoriteUseCase(characterId = characterId)

        combine(
            characterDetailFlow,
            isCharacterFavoriteFlow
        ) { characterDetailResult, isCharacterFavoriteResult ->
            when {
                characterDetailResult is Result.Error -> {
                    _uiState.update { CharacterDetailUiState.Error(characterDetailResult.message) }
                }

                isCharacterFavoriteResult is Result.Error -> {
                    _uiState.update { CharacterDetailUiState.Error(isCharacterFavoriteResult.message) }
                }

                characterDetailResult is Result.Success && isCharacterFavoriteResult is Result.Success -> {
                    _uiState.update {
                        CharacterDetailUiState.Success(
                            characterDetail = characterDetailResult.data,
                            isCharacterFavorite = isCharacterFavoriteResult.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleIsCharacterFavorite() {
        viewModelScope.launch {
            if (characterId != null) {
                val isCharacterFavoriteResult = isCharacterFavoriteUseCase(characterId).first()

                if (isCharacterFavoriteResult is Result.Success) {
                    val isCharacterFavorite = isCharacterFavoriteResult.data

                    if (isCharacterFavorite) {
                        deleteFavoriteCharacterUseCase(
                            FavoriteCharacter(id = characterId)
                        )
                    } else {
                        insertFavoriteCharacterUseCase(
                            FavoriteCharacter(id = characterId)
                        )
                    }
                }
            }
        }
    }
}