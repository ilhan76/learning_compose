package com.kudashov.learning_compose.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.screens.home.ui_data.TabData
import com.kudashov.learning_compose.ui.style.ProjectTextStyle
import com.kudashov.learning_compose.ui.theme.LearningComposeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(modifier.background(color = MaterialTheme.colorScheme.primary)) {
        Icon(
            painter = painterResource(id = R.drawable.surf_logo),
            contentDescription = "Surf Logo",
            modifier = modifier.padding(start = 32.dp, top = 32.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        SearchBar(modifier)

        VerticalStaggeredRoundedGrid(
            modifier = modifier,
            photos = viewModel.getPhotos().collectAsLazyPagingItems()
        )
    }
}

@Composable
private fun SearchBar(modifier: Modifier) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VerticalStaggeredRoundedGrid(
    modifier: Modifier,
    photos: LazyPagingItems<PhotoItem>
) = Box(
    modifier = modifier
        .padding(top = 24.dp, start = 22.dp, end = 22.dp)
        .fillMaxSize()
) {
    when (photos.loadState.refresh) {
        is LoadState.Loading -> {
            // todo Add shimmers
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        is LoadState.Error -> FullScreenPlaceholder(modifier)
        else -> {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
            ) {
                items(
                    count = photos.itemCount,
                    key = photos.itemKey(),
                    contentType = photos.itemContentType()
                ) { index ->
                    val item = photos[index]
                    Card(
                        modifier = modifier.padding(4.dp),
                        shape = RoundedCornerShape(
                            topStart = if (index == 0) 8.dp else 0.dp,
                            topEnd = if (index == 1) 8.dp else 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ) {
                        AsyncImage(model = item?.url, contentDescription = null)
                    }
                }
                if (photos.loadState.append is LoadState.Loading) addFooterLoader(modifier)
            }
        }
    }
}

@Composable
private fun FullScreenPlaceholder(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            modifier = modifier.padding(start = 32.dp, top = 32.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = modifier,
            text = "Упс, что-то пошло не так...",
            style = ProjectTextStyle.RegularText18Black
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyStaggeredGridScope.addFooterLoader(modifier: Modifier) {
    item(span = StaggeredGridItemSpan.FullLine) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            CircularProgressIndicator(
                modifier = modifier
                    .padding(8.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
private fun TabBarItem(
    modifier: Modifier = Modifier,
    onTabClicked: (String) -> Unit = {},
    tabData: TabData
) {
    Box(
        modifier = modifier.clickable {
            onTabClicked(tabData.id)
        }
    ) {
        tabData.hint?.let {
            Text(
                text = it,
                style = ProjectTextStyle.RegularText10Hint
            )
        }
        Text(
            text = tabData.title,
            modifier = modifier.padding(top = 16.dp, bottom = 12.dp),
            style = if (tabData.isNewFeature) ProjectTextStyle.RegularText18Green else ProjectTextStyle.RegularText18Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    LearningComposeTheme {
        TabBarItem(tabData = TabData("123", true, "Bka bla", "Qweqwe"))
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreviewDark() = LearningComposeTheme { HomeScreen() }

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() = LearningComposeTheme { HomeScreen() }