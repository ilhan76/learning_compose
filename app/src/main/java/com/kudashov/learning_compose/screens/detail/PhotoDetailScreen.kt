package com.kudashov.learning_compose.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.ui.style.ProjectTextStyle
import com.kudashov.learning_compose.ui.theme.LightTextColor

@Composable
fun PhotoDetailRoute(
    photoId: String,
    navController: NavController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    viewModel.loadPhotoDetail(photoId)

    PhotoDetailScreen(
        state = viewModel.state.value,
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
fun PhotoDetailScreen(
    state: PhotoDetailState,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    Box(modifier.fillMaxSize()) {
        AsyncImage(
            model = state.photoDetail?.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .background(LightTextColor)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = modifier
                .height(24.dp)
                .width(24.dp)
                .offset(x = 17.dp, y = 20.dp)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = "О фото",
                    modifier = modifier.background(color = MaterialTheme.colorScheme.tertiary)
                )
            }
            Spacer(
                modifier = modifier
                    .fillMaxHeight()
                    .width(16.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Share",
                    style = ProjectTextStyle.RegularText16Green,
                    modifier = modifier.padding(end = 11.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}