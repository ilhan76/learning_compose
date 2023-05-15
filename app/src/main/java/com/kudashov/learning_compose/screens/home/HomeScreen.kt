package com.kudashov.learning_compose.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
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
import com.kudashov.learning_compose.ui.theme.Grey
import com.kudashov.learning_compose.ui.theme.LearningComposeTheme
import com.kudashov.learning_compose.ui.theme.LightGrey

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun VerticalStaggeredRoundedGrid(
    modifier: Modifier,
    photos: LazyPagingItems<PhotoItem>
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            //fixme Разобраться, почему не работает
            isRefreshing = true
            photos.refresh()
        }
    )
    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .padding(top = 24.dp, start = 22.dp, end = 22.dp)
            .fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalItemSpacing = 9.dp
        ) {
            when {
                photos.loadState.refresh is LoadState.Loading && !isRefreshing-> addShimmers(modifier)
                photos.loadState.refresh is LoadState.Error -> addErrorPlaceholder(modifier)
                else -> {
                    if (photos.loadState.refresh is LoadState.NotLoading) isRefreshing = false
                    items(
                        count = photos.itemCount,
                        key = photos.itemKey(),
                        contentType = photos.itemContentType()
                    ) { index ->
                        PhotoGridItem(photos[index], index)
                    }
                }
            }
            if (photos.loadState.append is LoadState.Loading) addFooterLoader(modifier)
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

private fun LazyStaggeredGridScope.addErrorPlaceholder(modifier: Modifier) {
    item(span = StaggeredGridItemSpan.FullLine) {
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
}

@Composable
private fun PhotoGridItem(item: PhotoItem?, index: Int) {
    Card(
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

private fun LazyStaggeredGridScope.addShimmers(modifier: Modifier) {
    val list = listOf(96, 224, 224, 131, 131, 96)
    items(list) { ShimmerItem(modifier = modifier, height = it) }
}

private fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val stateOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1000))
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(Grey, LightGrey, Grey),
            start = Offset(stateOffsetX, 0f),
            end = Offset(stateOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}
@Composable
fun ShimmerItem(modifier: Modifier, height: Int) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .shimmerEffect()
    )
}

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