package com.example.narutoku.ui.screen.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.narutoku.model.SearchCharacter
import com.example.narutoku.navigation.Screen
import com.example.narutoku.ui.component.ErrorState
import com.example.narutoku.ui.screen.search.component.CharacterSearchListItem
import com.example.narutoku.ui.screen.search.component.SearchEmptyState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CharacterSearchScreen(
    navController: NavController,
    viewModel: CharacterSearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterSearchScreen(
        uiState = uiState,
        searchQuery = viewModel.searchQuery,
        onSearchQueryChange = { viewModel.updateSearchQuery(it) },
        onNavigateUp = { navController.navigateUp() },
        onCharacterClick = { character ->
            navController.navigate(Screen.CharacterDetail.route + "/${character.id}") {
                popUpTo(Screen.CharacterSearch.route) { inclusive = true }
            }
        },
        onErrorRetry = { viewModel.initializeUiState() }
    )
}

@Composable
fun CharacterSearchScreen(
    uiState: CharacterSearchUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onCharacterClick: (SearchCharacter) -> Unit,
    onErrorRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is CharacterSearchUiState.Success -> {
            CharacterSearchContent(
                searchResults = uiState.searchResults,
                searchQuery = searchQuery,
                isSearchResultsEmpty = uiState.queryHasNoResults,
                onSearchQueryChange = onSearchQueryChange,
                onNavigateUp = onNavigateUp,
                onCharacterClick = onCharacterClick,
                modifier = modifier
            )
        }

        is CharacterSearchUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onErrorRetry,
                onNavigateUp = onNavigateUp
            )
        }

        CharacterSearchUiState.Loading -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CharacterSearchContent(
    searchResults: SearchCharacter,
    searchQuery: String,
    isSearchResultsEmpty: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onCharacterClick: (SearchCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = { keyboardController?.hide() },
        placeholder = {
            Text(
                text = "Search characters",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                }
            }
        },
        content = {
            if (isSearchResultsEmpty) {
                SearchEmptyState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = 1,
                        itemContent = {
                            val cardShape = MaterialTheme.shapes.medium

                            CharacterSearchListItem(
                                searchCharacter = searchResults,
                                onCharacterClick = onCharacterClick,
                                cardShape = cardShape
                            )
                        }
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            dividerColor = MaterialTheme.colorScheme.surface,
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface
            )
        ),
        enabled = true,
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp,
        modifier = modifier.focusRequester(focusRequester)
    )

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    BackHandler(enabled = true) {
        onNavigateUp()
    }
}