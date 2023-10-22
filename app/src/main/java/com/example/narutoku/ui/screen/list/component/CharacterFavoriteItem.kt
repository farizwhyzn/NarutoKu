package com.example.narutoku.ui.screen.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.narutoku.R
import com.example.narutoku.model.Character

@Composable
fun CharacterFavoriteItem(
    character: Character,
    onCharacterClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageBuilder = remember(context) {
        ImageRequest.Builder(context = context)
            .decoderFactory(factory = SvgDecoder.Factory())
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .size(width = 140.dp, height = 200.dp)
            .clickable { onCharacterClick(character) }
    ) {
        Column {
            Column(modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (character.images?.size == 0) {
                        Image(
                            painterResource(id = R.drawable.blank_profile_picture),
                            contentDescription = "no photo",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    else{
                        AsyncImage(
                            model = imageBuilder
                                .data(character.images?.get(0))
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}