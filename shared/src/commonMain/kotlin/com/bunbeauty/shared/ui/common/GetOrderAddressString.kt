package com.bunbeauty.shared.ui.common

import androidx.compose.runtime.Composable
import com.bunbeauty.core.Constants.ADDRESS_DIVIDER
import com.bunbeauty.core.model.order.OrderAddress
import org.jetbrains.compose.resources.stringResource
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.msg_address_entrance_short
import papakarlo.shared.generated.resources.msg_address_flat_short
import papakarlo.shared.generated.resources.msg_address_floor_short
import papakarlo.shared.generated.resources.msg_address_house_short

@Composable
fun OrderAddress.getOrderAddressString(): String =
    (if (description.isNullOrEmpty()) {
        val houseShort = stringResource(Res.string.msg_address_house_short)
        val flatShort = stringResource(Res.string.msg_address_flat_short)
        val entranceShort = stringResource(Res.string.msg_address_entrance_short)
        val floorShort = stringResource(Res.string.msg_address_floor_short)
        street +
                getStringPart(
                    ADDRESS_DIVIDER,
                    houseShort,
                    house,
                ) +
                getStringPart(
                    ADDRESS_DIVIDER,
                    flatShort,
                    flat,
                ) +
                getInvertedStringPart(
                    ADDRESS_DIVIDER,
                    entrance,
                    entranceShort,
                ) +
                getInvertedStringPart(
                    ADDRESS_DIVIDER,
                    floor,
                    floorShort,
                ) +
                getStringPart(
                    ADDRESS_DIVIDER,
                    "",
                    comment,
                )
    } else {
        description
    }).toString()
