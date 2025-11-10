package com.bunbeauty.shared.ui.common.ui.element

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.placeholder_small

@Composable
fun FoodDeliveryAsyncImage(
    photoLink: String,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    error: Painter? = painterResource(Res.drawable.placeholder_small),
    placeholder: Painter? = painterResource(Res.drawable.placeholder_small),
) {
//    AsyncImage(
//        modifier = modifier,
//        model =
//            ImageRequest
//                .Builder(LocalContext.current)
//                .data(photoLink)
//                .crossfade(enable = true)
//                .build(),
//        placeholder = placeholder,
//        error = error,
//        contentDescription = contentDescription,
//        contentScale = contentScale,
//    )
}
