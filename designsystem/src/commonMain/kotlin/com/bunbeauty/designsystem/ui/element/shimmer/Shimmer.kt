package com.bunbeauty.designsystem.ui.element.shimmer

import androidx.compose.animation.core.LinearEasing
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme

@Composable
fun Shimmer(modifier: Modifier) {
    val durationMillis = 6_000
    val widthOfShadowBrush = 200
    val angleOfAxisY = 45f
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            )
        )
    )

    val shimmerColors =
        listOf(
            FoodDeliveryTheme.colors.mainColors.surfaceVariant
                .copy(alpha = 0.9f),
            FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                .copy(alpha = 0.4f),
            FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                .copy(alpha = 0.2f),
        )

    val brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnim.value - widthOfShadowBrush, y = 0.0f),
            end = Offset(x = translateAnim.value, y = angleOfAxisY),
            tileMode = TileMode.Mirror,
        )

    Spacer(
        modifier =
            modifier
                .drawWithContent {
                    drawContent()
                    drawRect(brush = brush)
                },
    )
}

@Preview(showBackground = true)
@Composable
private fun ShimmerPreview() {
    FoodDeliveryTheme {
        Shimmer(
            modifier =
                Modifier
                    .width(300.dp)
                    .height(64.dp),
        )
    }
}
