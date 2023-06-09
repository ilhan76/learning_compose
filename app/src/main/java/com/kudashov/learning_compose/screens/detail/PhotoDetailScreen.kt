package com.kudashov.learning_compose.screens.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.base.ui.style.ProjectTextStyle
import com.kudashov.learning_compose.base.ui.theme.Black
import com.kudashov.learning_compose.base.ui.theme.LearningComposeTheme
import com.kudashov.learning_compose.base.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun PhotoDetailRoute(
    topic: String,
    photoId: String,
    navController: NavController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadPhotoDetail(photoId)
        viewModel.loadPhotoStatistics(photoId)
    }

    PhotoDetailScreen(
        topic = topic,
        state = viewModel.state,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    topic: String,
    state: PhotoDetailState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    val interactionSource = remember { MutableInteractionSource() }

    BottomSheetScaffold(
        sheetDragHandle = null,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent(
                topic = topic,
                state = state,
                modifier = modifier
            ) {
                scope.launch { sheetState.hide() }
            }
        }
    ) {
        ScreenContent(
            state = state,
            navController = navController,
            modifier = modifier,
            openBottomSheet = { scope.launch { sheetState.expand() } }
        )
        if (sheetState.targetValue == SheetValue.Expanded) Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Black.copy(alpha = 0.4f))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    scope.launch { sheetState.hide() }
                }
        )
    }
}

@Composable
fun ScreenContent(
    state: PhotoDetailState,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: () -> Unit = {}
) {
    Box(modifier.fillMaxSize()) {
        AsyncImage(
            model = state.photoDetail?.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = White,
            modifier = modifier
                .padding(16.dp)
                .statusBarsPadding()
                .clickable {
                    navController.popBackStack()
                }
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { openBottomSheet() },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = stringResource(id = R.string.about_photo_btn_text),
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
                    text = stringResource(id = R.string.share_btn_text),
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

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TestPreview() {
    LearningComposeTheme {
    }
}