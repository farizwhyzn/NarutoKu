package com.example.narutoku.ui.screen.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.narutoku.common.Result
import com.example.narutoku.domain.GetCharacterSearchResultsUseCase
import com.example.narutoku.model.SearchCharacter
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val getCharacterSearchResultsUseCase: GetCharacterSearchResultsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CharacterSearchUiState>(CharacterSearchUiState.Loading)
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        initializeUiState()
    }

    fun initializeUiState() {
        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    val result = getCharacterSearchResultsUseCase(query)

                    when (result) {
                        is Result.Error -> {
                            _uiState.update {
                                CharacterSearchUiState.Error(
                                    message = result.message
                                )
                            }
                        }
                        is Result.Success -> {
                            val searchResults = result.data

                            _uiState.update {
                                CharacterSearchUiState.Success(
                                    searchResults = searchResults,
                                    queryHasNoResults = searchResults.equals(null)
                                )
                            }
                        }
                    }
                } else {
                    _uiState.update {
                        CharacterSearchUiState.Success(
                            searchResults = SearchCharacter(
                                id = null,
                                name = null,
                                images = null),
                            queryHasNoResults = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }
}
