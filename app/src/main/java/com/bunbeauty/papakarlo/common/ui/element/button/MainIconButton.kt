package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun MainIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes iconDescriptionStringId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.size(FoodDeliveryTheme.dimensions.buttonSize),
        colors = FoodDeliveryButtonDefaults.mainButtonColors,
        shape = mediumRoundedCornerShape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.icon24(),
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = stringResource(iconDescriptionStringId),
            tint = FoodDeliveryTheme.colors.mainColors.onPrimary
        )
    }
}

@Preview
@Composable
fun MainIconButtonPreview() {
    MainIconButton(
        iconId = R.drawable.ic_plus_16,
        iconDescriptionStringId = R.string.description_ic_add,
    ) {}
}