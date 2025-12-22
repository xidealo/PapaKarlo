package com.bunbeauty.designsystem.ui.element

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun rememberFoodDeliverySnackbarState(defaultText: String = ""): FoodDeliverySnackbarState {
    val coroutineScope = rememberCoroutineScope()
    return remember {
        _root_ide_package_.com.bunbeauty.designsystem.ui.element.FoodDeliverySnackbarState(
            coroutineScope = coroutineScope,
            defaultText = defaultText,
        )
    }
}

class FoodDeliverySnackbarState(
    private val coroutineScope: CoroutineScope,
    defaultText: String,
) {
    var job: Job? = null

    var isVisible: Boolean by mutableStateOf(false)
        private set

    var text: String by mutableStateOf(defaultText)
        private set

    fun show(newText: String) {
        job?.cancel()
        job =
            coroutineScope.launch {
                text = newText
                isVisible = true
                delay(2_000)
                isVisible = false
            }
    }

    fun show() {
        show(text)
    }
}

@Composable
fun FoodDeliverySnackbarBox(
    snackbarState: FoodDeliverySnackbarState,
    backgroundColor: Color = FoodDeliveryTheme.colors.mainColors.primary,
    textColor: Color = FoodDeliveryTheme.colors.mainColors.onPrimary,
    content: @Composable () -> Unit,
) {
    Box {
        content()
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = snackbarState.isVisible,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(backgroundColor)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(
                    text = snackbarState.text,
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = textColor,
                )
            }
        }
    }
}
