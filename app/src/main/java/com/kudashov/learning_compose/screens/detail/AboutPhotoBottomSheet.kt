package com.kudashov.learning_compose.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kudashov.learning_compose.R
import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.PhotoStatistics
import com.kudashov.learning_compose.base.ui.theme.Theme

@Composable
fun BottomSheetContent(
    topic: String,
    state: PhotoDetailState,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {}
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .background(color = Theme.colorScheme.primary)
) {
    val photoDetail = state.photoDetail
    val photoStatistics = state.photoStatistics

    Icon(
        painter = painterResource(id = R.drawable.ic_close),
        contentDescription = null,
        tint = Theme.colorScheme.tertiary,
        modifier = modifier
            .align(Alignment.TopEnd)
            .padding(16.dp)
            .clickable { onCloseClick() }
    )

    Column(modifier = modifier.padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 32.dp)) {
        photoDetail?.description?.let {
            Text(
                text = it,
                style = Theme.typography.semiBold.text24.standard,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.fillMaxWidth()
            )
        }

        Text(
            text = topic,
            style = Theme.typography.regular.text14.accent,
            modifier = modifier.padding(top = 6.dp)
        )

        photoStatistics?.let { Statistics(statistics = it, modifier = modifier) }

        photoDetail?.let { SpecialInfo(photoDetail = photoDetail, modifier = modifier) }

        Text(
            text = stringResource(id = R.string.license_text),
            style = Theme.typography.regular.text16.light,
            modifier = modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun Statistics(
    statistics: PhotoStatistics,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(24.dp))
    Row {
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.views_title_text),
                style = Theme.typography.regular.text14.light,
                modifier = modifier.heightIn(min = 24.dp)
            )
            Text(
                text = statistics.views.toString(),
                modifier = modifier.heightIn(min = 24.dp)
            )
        }
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.downloads_title_text),
                style = Theme.typography.regular.text14.light,
                modifier = modifier.heightIn(min = 24.dp)
            )
            Text(text = statistics.downloads.toString(), modifier = modifier.heightIn(min = 24.dp))
        }
    }
}

@Composable
private fun SpecialInfo(
    photoDetail: PhotoDetail,
    modifier: Modifier = Modifier
) {
    val hoursFromCreated = photoDetail.hoursFromCreated
    Spacer(modifier = modifier.height(24.dp))

    Text(
        text = stringResource(id = R.string.special_info_title_text),
        style = Theme.typography.regular.text14.light
    )

    photoDetail.country?.let { country ->
        Row(modifier = modifier.padding(top = 16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_data),
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            Text(text = country, style = Theme.typography.regular.text16.standard)
        }
    }
    Row(modifier = modifier.padding(top = 8.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_phone),
            contentDescription = null,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp)
        )
        Text(
            text = if (hoursFromCreated < 24) {
                "Published ${hoursFromCreated}h ago"
            } else {
                "Published on ${photoDetail.createdAtFormatted}"
            },
            style = Theme.typography.regular.text16.standard
        )
    }
}