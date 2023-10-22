package com.example.narutoku.ui.screen.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.narutoku.ui.component.SkeletonSurface

@Composable
fun CharacterDetailSkeletonLoader(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            SkeletonTopAppBar()
        },
        content = { scaffoldPadding ->
            SkeletonContent(modifier = Modifier.padding(scaffoldPadding))
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkeletonTopAppBar(modifier: Modifier = Modifier) {
    LargeTopAppBar(
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {},
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.StarOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}

@Composable
private fun SkeletonContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        SkeletonSurface(
            modifier = Modifier
                .fillMaxWidth()
                .height(374.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "text 1",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        SkeletonSurface(
            modifier = Modifier
                .fillMaxWidth()
                .height(91.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "text 2",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        SkeletonSurface(
            shape = MaterialTheme.shapes.medium.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}