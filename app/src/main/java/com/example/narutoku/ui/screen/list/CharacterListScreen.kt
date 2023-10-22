package com.example.narutoku.ui.screen.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.narutoku.model.Character
import com.example.narutoku.navigation.Screen
import com.example.narutoku.ui.component.ErrorState
import com.example.narutoku.ui.screen.list.component.CharacterFavoriteItem
import com.example.narutoku.ui.screen.list.component.CharacterListItem
import com.example.narutoku.ui.screen.list.component.CharacterListSkeletonLoader
import com.example.narutoku.ui.screen.list.component.CharactersEmptyState
import com.example.narutoku.ui.screen.list.component.FavouriteCharactersEmptyState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterListScreen(
        uiState = uiState,
        onCharacterClick = { character ->
            navController.navigate(Screen.CharacterDetail.route + "/${character.id}")
        },
        onNavigateSearch = { navController.navigate(Screen.CharacterSearch.route) },
        onErrorRetry = { viewModel.initializeUiState() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CharacterListScreen(
    uiState: CharacterListUiState,
    onCharacterClick: (Character) -> Unit,
    onNavigateSearch: () -> Unit,
    onErrorRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    when (uiState) {
        is CharacterListUiState.Success -> {
            val lazyListState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            val showButton by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex > 0
                }
            }

            Scaffold(
                topBar = {
                    CharacterListTopBar(
                        scrollBehavior = scrollBehavior,
                        onNavigateSearch = onNavigateSearch
                    )
                },
                content = { scaffoldPadding ->
                    CharacterListContent(
                        characters = uiState.characters,
                        favoriteCharacters = uiState.favoriteCharacters,
                        onCharacterClick = onCharacterClick,
                        lazyListState = lazyListState,
                        modifier = Modifier.padding(scaffoldPadding)
                    )
                },
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = showButton,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        SmallFloatingActionButton(
                            onClick = {
                                scope.launch {
                                    scrollBehavior.state.heightOffset = 0f
                                    lazyListState.animateScrollToItem(0)
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            content = {
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowUp,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                },
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            )
        }

        is CharacterListUiState.Loading -> {
            CharacterListSkeletonLoader()
        }

        is CharacterListUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onErrorRetry
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterListTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "NarutoKu",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.offset(x = (-4).dp)
            )
        },
        actions = {
            IconButton(onClick = onNavigateSearch) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "search"
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
private fun CharacterListContent(
    characters: ImmutableList<Character>,
    favoriteCharacters: ImmutableList<Character>,
    onCharacterClick: (Character) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(start = 12.dp, top = 12.dp),
        modifier = modifier
    ) {
        item {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            if (favoriteCharacters.isEmpty()) {
                FavouriteCharactersEmptyState(
                    modifier = Modifier.padding(end = 12.dp)
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(end = 12.dp)
                ) {
                    items(
                        count = favoriteCharacters.size,
                        key = { favoriteCharacters[it].id },
                        itemContent = { index ->
                            val favoriteCharacterItem = favoriteCharacters[index]

                            CharacterFavoriteItem(
                                character = favoriteCharacterItem,
                                onCharacterClick = { onCharacterClick(favoriteCharacterItem) }
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Characters",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

        }

        if (characters.isEmpty()) {
            item {
                CharactersEmptyState()
            }
        } else {
            items(
                count = characters.size,
                key = { characters[it].id },
                itemContent = { index ->
                    val characterListItem = characters[index]

                    val cardShape = when (index) {
                        0 -> MaterialTheme.shapes.medium.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )

                        characters.lastIndex -> MaterialTheme.shapes.medium.copy(
                            topStart = CornerSize(0.dp),
                            topEnd = CornerSize(0.dp)
                        )

                        else -> RoundedCornerShape(0.dp)
                    }

                    CharacterListItem(
                        character = characterListItem,
                        onCharacterClick = { onCharacterClick(characterListItem) },
                        cardShape = cardShape,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            )
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Cannot find character",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Search for character",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(12.dp))
            }
        }

    }
}