package com.bunbeauty.shared.ui.common.ui.screen.bottomsheet


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.bold
import org.jetbrains.compose.resources.StringResource
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.common_email

@Composable
fun FoodDeliveryBottomSheet(
    titleStringId: StringResource,
    content: @Composable ColumnScope.() -> Unit,
) {
    FoodDeliveryBottomSheet(
        title = stringResource(resource = titleStringId),
        content = content,
    )
}

@Composable
fun FoodDeliveryBottomSheet(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    FoodDeliveryBottomSheet(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                textAlign = TextAlign.Center,
            )
        },
        content = content,
    )
}

@Composable
private fun FoodDeliveryBottomSheet(
    title: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(FoodDeliveryBottomSheetDefaults.bottomSheetShape)
                .background(FoodDeliveryTheme.colors.mainColors.surface)
                .padding(horizontal = FoodDeliveryTheme.dimensions.screenContentSpace)
                .padding(bottom = FoodDeliveryTheme.dimensions.screenContentSpace, top = 8.dp),
    ) {
        Spacer(
            modifier =
                Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .background(
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        shape = RoundedCornerShape(2.dp),
                    ).align(CenterHorizontally),
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
        FoodDeliveryBottomSheet(titleStringId = Res.string.common_email) {
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}
