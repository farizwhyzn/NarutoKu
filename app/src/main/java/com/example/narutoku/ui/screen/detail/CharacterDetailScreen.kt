package com.example.narutoku.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.narutoku.model.CharacterDetail
import com.example.narutoku.ui.component.ErrorState
import com.example.narutoku.ui.screen.detail.component.CharacterDetailSkeletonLoader

@Composable
fun CharacterDetailScreen(
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterDetailScreen(
        uiState = uiState,
        onNavigateUp = { navController.navigateUp() },
        onClickFavoriteCharacter = { viewModel.toggleIsCharacterFavorite() },
        onErrorRetry = { viewModel.initializeUiState() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    uiState: CharacterDetailUiState,
    onNavigateUp: () -> Unit,
    onClickFavoriteCharacter: () -> Unit,
    onErrorRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    when (uiState) {
        is CharacterDetailUiState.Success -> {
            Scaffold(
                topBar = {
                    CharacterDetailTopBar(
                        characterDetail = uiState.characterDetail,
                        isCharacterFavorite = uiState.isCharacterFavorite,
                        onNavigateUp = onNavigateUp,
                        onClickFavoriteCharacter = onClickFavoriteCharacter,
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { scaffoldPadding ->
                    CharacterDetailContent(
                        characterDetail = uiState.characterDetail,
                        modifier = Modifier.padding(scaffoldPadding)
                    )
                },
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            )
        }

        is CharacterDetailUiState.Loading -> {
            CharacterDetailSkeletonLoader()
        }

        is CharacterDetailUiState.Error -> {
            ErrorState(
                message = uiState.message,
                onRetry = onErrorRetry,
                onNavigateUp = onNavigateUp
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterDetailTopBar(
    characterDetail: CharacterDetail,
    isCharacterFavorite: Boolean,
    onNavigateUp: () -> Unit,
    onClickFavoriteCharacter: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageBuilder = remember(context) {
        ImageRequest.Builder(context = context)
            .decoderFactory(factory = SvgDecoder.Factory())
    }

    LargeTopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = characterDetail.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = characterDetail.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (characterDetail.images?.size == 0) {
//                Image()
                } else {
                    AsyncImage(
                        model = imageBuilder
                            .data(characterDetail.images?.get(0))
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(44.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onClickFavoriteCharacter) {
                Icon(
                    imageVector = if (isCharacterFavorite) {
                        Icons.Rounded.Star
                    } else {
                        Icons.Rounded.StarOutline
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
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
private fun CharacterDetailContent(
    characterDetail: CharacterDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            .testTag("character_detail_content")
    ) {
        Text(
            text = "text 1",
            style = MaterialTheme.typography.titleMedium
        )
    }
}