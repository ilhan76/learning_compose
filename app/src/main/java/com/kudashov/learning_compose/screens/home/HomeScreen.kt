package com.kudashov.learning_compose.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.network.RetrofitClient
import com.kudashov.learning_compose.network.home.HomeApi
import com.kudashov.learning_compose.network.home.HomeRepository
import com.kudashov.learning_compose.ui.theme.LearningComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(modifier.background(color = MaterialTheme.colorScheme.primary)) {
        Icon(
            painter = painterResource(id = R.drawable.surf_logo),
            contentDescription = "Surf Logo",
            modifier = modifier.padding(start = 32.dp, top = 32.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        SearchBar(modifier)

        LazyVerticalStaggeredGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 22.dp, end = 22.dp),
            columns = StaggeredGridCells.Fixed(2),
        ) {
            items(state.photos) { item ->
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.url)
                            .size(coil.size.Size.ORIGINAL)
                            .build(),
                    ),
                    contentDescription = "",
                    modifier = modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier) {
    var inputText by remember { mutableStateOf("") }
    TextField(
        value = inputText,
        onValueChange = { inputText = it },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = ""
            )
        },
        placeholder = { Text(text = "Search photo") },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp),
    )
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreviewDark() {
    LearningComposeTheme {
        HomeScreen(
            viewModel = HomeViewModel(
                HomeRepository(
                    RetrofitClient.getClient().create(HomeApi::class.java)
                )
            )
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun DefaultPreview() {
    LearningComposeTheme {
        HomeScreen(
            viewModel = HomeViewModel(
                HomeRepository(
                    RetrofitClient.getClient().create(HomeApi::class.java)
                )
            )
        )
    }
}