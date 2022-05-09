package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem

@Composable
fun CafeItem(
    modifier: Modifier = Modifier,
    cafeItem: CafeItem,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = FoodDeliveryTheme.dimensions.elevation,
                shape = mediumRoundedCornerShape
            )
            .clip(mediumRoundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Text(
                text = cafeItem.address,
                modifier = Modifier.fillMaxWidth(),
                style = FoodDeliveryTheme.typography.h2,
                color = FoodDeliveryTheme.colors.onSurface
            )
            Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                Text(
                    text = cafeItem.workingHours,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = FoodDeliveryTheme.dimensions.smallSpace),
                    text = cafeItem.isOpenMessage,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.cafeStatusColor(cafeItem.cafeStatus),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CafeItemOpenPreview() {
    CafeItem(
        cafeItem = CafeItem(
            uuid = "",
            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
            workingHours = "9:00 - 22:00",
            isOpenMessage = "Открыто",
            cafeStatus = CafeStatus.OPEN,
        )
    ) {}
}

@Preview
@Composable
private fun CafeItemCloseSoonPreview() {
    CafeItem(
        cafeItem = CafeItem(
            uuid = "",
            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
            workingHours = "9:00 - 22:00",
            isOpenMessage = "Открыто. Закроется через 30 минут",
            cafeStatus = CafeStatus.CLOSE_SOON,
        )
    ) {}
}

@Preview
@Composable
private fun CafeItemClosedPreview() {
    CafeItem(
        cafeItem = CafeItem(
            uuid = "",
            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
            workingHours = "9:00 - 22:00",
            isOpenMessage = "Закрыто",
            cafeStatus = CafeStatus.CLOSED,
        )
    ) {}
}
