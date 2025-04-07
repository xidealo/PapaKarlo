package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bunbeauty.papakarlo.R

@Composable
fun FoodDeliveryAsyncImage(
    photoLink: String,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    error: Painter? = painterResource(R.drawable.placeholder_small),
    placeholder: Painter? = painterResource(R.drawable.placeholder_small)
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(photoLink)
            .crossfade(enable = true)
            .build(),
        placeholder = placeholder,
        error = error,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}
