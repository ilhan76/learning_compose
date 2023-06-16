package com.kudashov.learning_compose.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoItem
import com.kudashov.learning_compose.base.domain.util.LoadStatus
import com.kudashov.learning_compose.base.domain.util.LoadableData
import com.kudashov.learning_compose.base.navigation.Screen
import com.kudashov.learning_compose.base.paging.PagerLoadStatus
import com.kudashov.learning_compose.base.paging.LoadDataType
import com.kudashov.learning_compose.base.ui.theme.Dark
import com.kudashov.learning_compose.base.ui.theme.DarkGrey
import com.kudashov.learning_compose.screens.home.ui_data.TabItem
import com.kudashov.learning_compose.base.ui.theme.LearningComposeTheme
import com.kudashov.learning_compose.base.ui.theme.LightGreen
import com.kudashov.learning_compose.base.ui.theme.SuperLightGreen
import com.kudashov.learning_compose.base.ui.theme.Theme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val isRefreshing = state.loadStatus == PagerLoadStatus.PullRefreshLoading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.loadPhotos(LoadDataType.PullRefresh) }
    )
    val isDark = remember { viewModel.isDarkTheme }

    LaunchedEffect(Unit) {
        viewModel.loadPhotos()
        viewModel.loadTopics()
    }

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        ScrollableContent(
            state = state,
            viewModel = viewModel,
            onItemClicked = { id ->
                navController.navigate("${Screen.Detail.route}/${viewModel.state.selectedTopic}/$id")
            },
            modifier = modifier
        )

        ThemeSwitch(
            isDark = isDark.value,
            modifier = modifier.align(Alignment.BottomCenter),
            onSwitchClicked = { viewModel.updateTheme(it) }
        )

        //fixme - Найти более красивое решение
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 250.dp),
            contentColor = Theme.colorScheme.tertiary
        )
    }
}

@Composable
private fun ScrollableContent(
    state: HomeState,
    viewModel: HomeViewModel,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(color = Theme.colorScheme.primary),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        verticalItemSpacing = 9.dp
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.surf_logo),
                    contentDescription = null,
                    tint = Theme.colorScheme.onPrimary,
                    modifier = modifier
                        .padding(top = 32.dp)
                        .statusBarsPadding(),
                )
                SearchBar(modifier)
                PageList(
                    state = state,
                    modifier = modifier,
                    onTabClicked = viewModel::onTabClicked
                )
            }
        }

        when (state.selectedTopicId) {
            ItemCreator.RANDOM_PHOTO_ID -> addRandomPhoto(
                loadableData = state.randomPhoto,
                modifier = modifier
            )

            else -> addVerticalStaggeredRoundedGrid(
                photos = state.photos,
                pagerLoadStatus = state.loadStatus,
                modifier = modifier,
                onItemClicked = onItemClicked,
                loadNextPage = {
                    viewModel.loadPhotos(LoadDataType.Append)
                }
            )
        }
    }
}

@Composable
private fun ThemeSwitch(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onSwitchClicked: (Boolean) -> Unit,
) = Box(modifier = modifier.padding(bottom = 24.dp)) {
    val selectedTextStyle = Theme.typography.regular.text16.standard
    val notSelectedTextStyle = Theme.typography.regular.text16.light

    Row(
        modifier = modifier
            .navigationBarsPadding()
            .height(50.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = Theme.colorScheme.secondary
            )
            .background(
                color = Theme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.light_theme_text),
            style = if (!isDark) selectedTextStyle else notSelectedTextStyle,
            modifier = modifier
                .width(75.dp)
                .padding(horizontal = 5.dp)
        )
        Switch(
            checked = isDark,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DarkGrey,
                checkedTrackAlpha = 1f,
                checkedTrackColor = Dark,
                uncheckedThumbColor = LightGreen,
                uncheckedTrackColor = SuperLightGreen,
                uncheckedTrackAlpha = 0.38f
            ),
            onCheckedChange = onSwitchClicked
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.dark_theme_text),
            style = if (isDark) selectedTextStyle else notSelectedTextStyle,
            modifier = modifier
                .width(75.dp)
                .padding(horizontal = 5.dp)
        )
    }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    TextField(
        value = inputText,
        onValueChange = { inputText = it },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = null
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Theme.colorScheme.secondary,
            unfocusedContainerColor = Theme.colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
            .heightIn(min = 40.dp),
    )
}

@Composable
private fun PageList(
    state: HomeState,
    modifier: Modifier = Modifier,
    onTabClicked: (String) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(state.tabs) { tabData ->
            when (tabData) {
                is TabItem.TextTabItem -> TabBarItem(
                    textTabItem = tabData,
                    modifier = modifier,
                    onTabClicked = onTabClicked
                )

                TabItem.Divider -> Box(
                    modifier = modifier
                        .height(38.dp)
                        .width(1.dp)
                        .background(color = Theme.colorScheme.onPrimary.copy(alpha = 0.16f))
                )
            }
        }
    }
}

private fun LazyStaggeredGridScope.addVerticalStaggeredRoundedGrid(
    photos: List<PhotoItem>,
    pagerLoadStatus: PagerLoadStatus,
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit = {},
    loadNextPage: () -> Unit = {}
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        Spacer(
            modifier = modifier
                .height(24.dp)
                .fillMaxWidth()
        )
    }
    when (pagerLoadStatus) {
        PagerLoadStatus.MainLoading -> addShimmers(modifier)

        PagerLoadStatus.Error -> addErrorPlaceholder(modifier)

        else -> {
            itemsIndexed(photos) { index, photo ->
                if (index == photos.size - 1) loadNextPage()
                PhotoGridItem(
                    item = photo,
                    index = index,
                    modifier = modifier.clickable {
                        onItemClicked(photo.id)
                    }
                )
            }
        }
    }
    if (pagerLoadStatus == PagerLoadStatus.AppendLoading) addFooterLoader(modifier)
}

private fun LazyStaggeredGridScope.addRandomPhoto(
    loadableData: LoadableData<PhotoDetail>,
    modifier: Modifier = Modifier
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        val boxModifier = if (loadableData.isLoading) {
            modifier.height(420.dp)
        } else {
            modifier
        }
        Box(
            modifier = boxModifier
                .padding(start = 24.dp, end = 24.dp, top = 32.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colorScheme.secondary,
                            Theme.colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(8.dp),
                )
        ) {
            when (loadableData.loadStatus) {
                LoadStatus.Loading -> {
                    Image(
                        painter = painterResource(
                            id = if (isSystemInDarkTheme()) R.drawable.ic_loader_dark
                            else R.drawable.ic_loader_light
                        ),
                        contentDescription = null,
                        modifier = modifier
                            .height(40.dp)
                            .width(40.dp)
                            .align(Alignment.Center)
                    )
                }

                LoadStatus.Loaded -> Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = loadableData.data?.url,
                        contentDescription = null,
                        modifier = modifier.fillMaxWidth()
                    )
                }

                LoadStatus.Error -> {}
            }

        }
    }
}

@Composable
private fun PhotoGridItem(
    item: PhotoItem?,
    index: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(
            topStart = if (index == 0) 8.dp else 0.dp,
            topEnd = if (index == 1) 8.dp else 0.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        modifier = modifier
    ) {
        AsyncImage(model = item?.url, contentDescription = null)
    }
}

@Composable
fun ShimmerItem(height: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .shimmerEffect()
    )
}

@Composable
private fun TabBarItem(
    textTabItem: TabItem.TextTabItem,
    modifier: Modifier = Modifier,
    onTabClicked: (String) -> Unit = {},
) {
    val boxModifier = if (textTabItem.isSelected) modifier
        .bottomBorder(
            color = Theme.colorScheme.onPrimary,
            lineHeight = with(LocalDensity.current) { 2.dp.toPx() }
        )
    else modifier
    Box(
        boxModifier
            .clickable {
                onTabClicked(textTabItem.id)
            }
    ) {
        if (textTabItem.isNewFeature) {
            Text(
                text = stringResource(id = R.string.new_feature_hint),
                style = Theme.typography.regular.text10.hint,
                modifier = modifier.padding(top = 2.dp)
            )
        }
        Text(
            text = textTabItem.title,
            modifier = modifier.padding(top = 16.dp, bottom = 12.dp),
            style = when {
                textTabItem.isNewFeature -> Theme.typography.regular.text14.accent
                textTabItem.isSelected -> Theme.typography.regular.text14.standard
                else -> Theme.typography.regular.text14.light
            }
        )
    }
}

private fun LazyStaggeredGridScope.addErrorPlaceholder(modifier: Modifier = Modifier) {
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
                tint = Theme.colorScheme.error
            )
            Text(
                modifier = modifier,
                text = stringResource(id = R.string.error_placeholder_text),
                style = Theme.typography.regular.text16.standard
            )
        }
    }
}

private fun LazyStaggeredGridScope.addShimmers(modifier: Modifier = Modifier) {
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
            colors = listOf(
                Theme.colorScheme.secondary,
                Theme.colorScheme.primary,
                Theme.colorScheme.secondary
            ),
            start = Offset(stateOffsetX, 0f),
            end = Offset(stateOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}

fun Modifier.bottomBorder(color: Color, lineHeight: Float) = drawBehind {
    val y = size.height - lineHeight

    drawRect(
        color = color,
        topLeft = Offset(0f, y),
        size = Size(size.width, lineHeight)
    )
}

private fun LazyStaggeredGridScope.addFooterLoader(modifier: Modifier = Modifier) {
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
                color = Theme.colorScheme.tertiary,
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "TabBarItem"
)
@Composable
fun TestPreview() {
    LearningComposeTheme {
        TabBarItem(textTabItem = TabItem.TextTabItem("123", true, "Bka bla", true))
    }
}
