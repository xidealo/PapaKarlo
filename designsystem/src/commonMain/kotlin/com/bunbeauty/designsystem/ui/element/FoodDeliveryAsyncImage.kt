package com.bunbeauty.designsystem.ui.element

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.placeholder_small

@Composable
fun FoodDeliveryAsyncImage(
    photoLink: String,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    error: Painter? = painterResource(Res.drawable.placeholder_small),
    placeholder: Painter? = painterResource(Res.drawable.placeholder_small),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(context = LocalPlatformContext.current)
            .data(data = photoLink)
            .memoryCachePolicy(policy = CachePolicy.ENABLED)
            .diskCachePolicy(policy = CachePolicy.ENABLED)
            .crossfade(enable = true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        error = error,
        //placeholder = placeholder
    )
}
