package com.example.narutoku.ui.screen.list.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.narutoku.R
import com.example.narutoku.model.Character

@Composable
fun CharacterListItem(
    character: Character,
    onCharacterClick: (Character) -> Unit,
    cardShape: Shape,
    modifier : Modifier = Modifier
) {
    val context = LocalContext.current

    val imageBuilder = remember(context) {
        ImageRequest.Builder(context = context)
            .decoderFactory(factory = SvgDecoder.Factory())
    }

    Surface(
        shape = cardShape,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCharacterClick(character) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            if (character.images?.size == 0) {
                Image(painterResource(id = R.drawable.blank_profile_picture),
                    contentDescription = "no photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                )
            } else {
                AsyncImage(
                    model = imageBuilder
                        .data(character.images?.get(0))
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
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