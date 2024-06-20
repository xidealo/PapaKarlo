package com.bunbeauty.papakarlo.common.ui.element.simmer

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun Shimmer(
    modifier: Modifier
) {
    val durationMillis = 2_000
    val widthOfShadowBrush = 200
    val angleOfAxisY = 45f
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val shimmerColors = listOf(
        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant.copy(alpha = 0.6f),
        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant.copy(alpha = 0.3f),
        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant.copy(alpha = 0.6f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
        tileMode = TileMode.Mirror
    )

    Spacer(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawRect(brush = brush)
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ShimmerPreview() {
    FoodDeliveryTheme {
        Shimmer(
            modifier = Modifier
                .width(300.dp)
                .height(64.dp)
        )
    }
}
