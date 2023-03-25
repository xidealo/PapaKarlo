package com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold

@Composable
fun FoodDeliveryLazyBottomSheet(
    @StringRes titleStringId: Int,
    scrolledToTop: ((Boolean) -> Unit),
    bottomContent: @Composable ColumnScope.() -> Unit = {},
    content: LazyListScope.() -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val itemPosition by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { itemPosition }.collect { itemPosition ->
            scrolledToTop(itemPosition == 0)
        }
    }
    FoodDeliveryBottomSheet(
        title = stringResource(id = titleStringId)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f, false),
            state = lazyListState,
            content = content,
        )
        bottomContent()
    }
}

@Composable
fun FoodDeliveryBottomSheet(
    @StringRes titleStringId: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    FoodDeliveryBottomSheet(
        title = stringResource(id = titleStringId),
        content = content
    )
}

@Composable
fun FoodDeliveryBottomSheet(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    FoodDeliveryBottomSheet(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                textAlign = TextAlign.Center
            )
        },
        content = content
    )
}

@Composable
private fun FoodDeliveryBottomSheet(
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FoodDeliveryBottomSheetDefaults.bottomSheetShape)
            .background(FoodDeliveryTheme.colors.mainColors.surface)
            .padding(horizontal = FoodDeliveryTheme.dimensions.screenContentSpace)
            .padding(bottom = FoodDeliveryTheme.dimensions.screenContentSpace, top = 8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .width(32.dp)
                .height(4.dp)
                .background(
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    shape = RoundedCornerShape(2.dp)
                )
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        title()
        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

@Preview
@Composable
fun FoodDeliveryBottomSheetPreview() {
    FoodDeliveryTheme {
        FoodDeliveryBottomSheet(titleStringId = R.string.common_email) {
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}
