package com.example.narutoku.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.narutoku.R
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

                    if (characterDetail.personal?.affiliation?.get(0).isNullOrEmpty()) {
                        Text(
                            text = "No nation",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = characterDetail.personal?.affiliation?.get(0)!!,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (characterDetail.images?.size == 0) {
                    Image(
                        painterResource(id = R.drawable.blank_profile_picture),
                        contentDescription = "no photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                    )
                } else {
                    AsyncImage(
                        model = imageBuilder
                            .data(characterDetail.images?.get(0))
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(60.dp)
                            .clip(CircleShape)
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
            text = "Personal details",
            style = MaterialTheme.typography.titleMedium
        )

        if (characterDetail.personal?.sex.isNullOrEmpty()) {
            Text(
                text = "Sex: -",
            )
        } else {
            Text(
                text = "Sex: " + characterDetail.personal?.sex!!,
            )
        }

        if (characterDetail.personal?.birthDate.isNullOrEmpty()) {
            Text(
                text = "Birth Date: -",
            )
        } else {
            Text(
                text = "Birth Date: " + characterDetail.personal?.birthDate!!,
            )
        }

        if (characterDetail.personal?.status.isNullOrEmpty()) {
            Text(
                text = "Status: -",
            )
        } else {
            Text(
                text = "Status: " + characterDetail.personal?.status!!,
            )
        }

        if (characterDetail.personal?.clan.isNullOrEmpty()) {
            Text(
                text = "Clan: -",
            )
        } else {
            Text(
                text = "Clan: " + characterDetail.personal?.clan!!,
            )
        }

        if (characterDetail.personal?.occupation.isNullOrEmpty()) {
            Text(
                text = "Occupation: -",
            )
        } else {
            Text(
                text = "Occupation: " + characterDetail.personal?.occupation!!,
            )
        }
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Debut Episode",
            style = MaterialTheme.typography.titleMedium
        )

        if (characterDetail.debut?.anime.isNullOrEmpty()) {
            Text(
                text = "-",
            )
        } else {
            Text(
                text = characterDetail.debut?.anime.toString(),
            )
        }
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Jutsu",
            style = MaterialTheme.typography.titleMedium
        )

        if (characterDetail.jutsu?.size == 0) {
            Text(
                text = "-",
            )
        } else {
            for (jutsuItem in characterDetail.jutsu!!) {
                Text(
                    text = jutsuItem.toString(),
                )
            }
        }
    }
}