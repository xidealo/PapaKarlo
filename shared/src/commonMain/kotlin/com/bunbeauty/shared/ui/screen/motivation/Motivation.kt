package com.bunbeauty.shared.ui.screen.motivation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_delivery
import papakarlo.designsystem.generated.resources.ic_warning
import papakarlo.designsystem.generated.resources.msg_consumer_cart_for_free_delivery
import papakarlo.designsystem.generated.resources.msg_consumer_cart_for_lower_delivery
import papakarlo.designsystem.generated.resources.msg_consumer_cart_free_delivery
import papakarlo.designsystem.generated.resources.msg_consumer_cart_lower_delivery
import papakarlo.designsystem.generated.resources.msg_consumer_cart_min_order

@Composable
fun Motivation(
    motivation: MotivationUi?,
    modifier: Modifier = Modifier,
) {
    motivation ?: return

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Absolute.spacedBy(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp),
        ) {
            val iconId =
                when (motivation) {
                    is MotivationUi.MinOrderCost -> Res.drawable.ic_warning
                    is MotivationUi.ForLowerDelivery,
                    is MotivationUi.LowerDeliveryAchieved,
                    -> Res.drawable.ic_delivery
                }
            val iconTint =
                when (motivation) {
                    is MotivationUi.MinOrderCost -> {
                        FoodDeliveryTheme.colors.statusColors.warning
                    }

                    is MotivationUi.ForLowerDelivery,
                    is MotivationUi.LowerDeliveryAchieved,
                    -> {
                        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                    }
                }
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                tint = iconTint,
                contentDescription = null,
            )

            val motivationText =
                when (motivation) {
                    is MotivationUi.MinOrderCost -> {
                        buildAnnotatedStringWithBold(
                            text = stringResource(Res.string.msg_consumer_cart_min_order, motivation.cost),
                            subtextToSelect = motivation.cost,
                        )
                    }

                    is MotivationUi.ForLowerDelivery -> {
                        val textId =
                            if (motivation.isFree) {
                                Res.string.msg_consumer_cart_for_free_delivery
                            } else {
                                Res.string.msg_consumer_cart_for_lower_delivery
                            }
                        buildAnnotatedStringWithBold(
                            text = stringResource(textId, motivation.increaseAmountBy),
                            subtextToSelect = motivation.increaseAmountBy,
                        )
                    }

                    is MotivationUi.LowerDeliveryAchieved -> {
                        val textId =
                            if (motivation.isFree) {
                                Res.string.msg_consumer_cart_free_delivery
                            } else {
                                Res.string.msg_consumer_cart_lower_delivery
                            }
                        buildAnnotatedString {
                            append(stringResource(textId))
                        }
                    }
                }
            Text(
                modifier =
                    Modifier
                        .animateContentSize(tween(500))
                        .weight(1f),
                text = motivationText,
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
        }

        when (motivation) {
            is MotivationUi.MinOrderCost -> null
            is MotivationUi.ForLowerDelivery -> motivation.progress
            is MotivationUi.LowerDeliveryAchieved -> 1f
        }?.let { progress ->
            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(500),
                label = "progress",
            )
            val animatedColor by animateColorAsState(
                targetValue =
                    lerp(
                        FoodDeliveryTheme.colors.statusColors.warning,
                        FoodDeliveryTheme.colors.statusColors.positive,
                        progress,
                    ),
                animationSpec = tween(500),
                label = "color",
            )
            LinearProgressIndicator(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                progress = animatedProgress,
                color = animatedColor,
                trackColor = FoodDeliveryTheme.colors.mainColors.disabled,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun buildAnnotatedStringWithBold(
    text: String,
    subtextToSelect: String,
): AnnotatedString =
    buildAnnotatedString {
        val startIndex = text.indexOf(subtextToSelect)
        val endIndex = startIndex + subtextToSelect.length
        append(text)
        addStyle(
            style =
                FoodDeliveryTheme.typography.bodyMedium.bold
                    .toSpanStyle(),
            start = startIndex,
            end = endIndex,
        )
    }
