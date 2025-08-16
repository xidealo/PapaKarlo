package com.bunbeauty.papakarlo.common.ui.screen.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryModalBottomSheet(
    onDismissRequest: () -> Unit,
    isShown: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    contentPadding: PaddingValues = PaddingValues(
        top = 8.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 16.dp
    ),
    shape: Shape = FoodDeliveryBottomSheetDefaults.bottomSheetShape,
    containerColor: Color = FoodDeliveryTheme.colors.mainColors.surface,
    contentColor: Color = contentColorFor(containerColor),
    dragHandle: @Composable (() -> Unit)? = { FoodDeliveryBottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit
) {
    val context = LocalContext.current

    var isVisible by remember {
        mutableStateOf(false)
    }

    val sheetState = remember {
        SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Hidden,
            density = Density(context = context)
        )
    }

    LaunchedEffect(isShown) {
        if (!isShown) {
            sheetState.hide()
        }

        isVisible = isShown
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            dragHandle = dragHandle,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = contentPadding)
                //    .padding(bottom = systemBottomBarHeight)
            ) {
                title?.let {
                    Title(
                        modifier = Modifier.padding(vertical = 16.dp),
                        title = title
                    )
                }
                content()
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = title,
        style = FoodDeliveryTheme.typography.titleMedium.bold,
        color = FoodDeliveryTheme.colors.mainColors.onSurface,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun FoodDeliveryModalBottomSheetPreview() {
    var isShownState by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(100)
        isShownState = true
    }
    FoodDeliveryTheme {
        FoodDeliveryModalBottomSheet(
            onDismissRequest = {},
            isShown = isShownState
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)
            ) {
                repeat(4) {
                    Spacer(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .background(FoodDeliveryTheme.colors.mainColors.surface)
                    )
                }
            }
        }
    }
}
