package com.bunbeauty.core.extension

import androidx.compose.runtime.Composable
import com.bunbeauty.core.Constants.ADDRESS_DIVIDER
import com.bunbeauty.core.model.UserAddressItem
import com.bunbeauty.core.model.address.SelectableUserAddress
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.model.address.UserAddressWithCity
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.msg_address_entrance_short
import papakarlo.designsystem.generated.resources.msg_address_flat_short
import papakarlo.designsystem.generated.resources.msg_address_floor_short
import papakarlo.designsystem.generated.resources.msg_address_house_short

@Composable
fun UserAddressWithCity.toAddressString(): String = city + ADDRESS_DIVIDER + userAddress?.toAddressString()

@Composable
fun SelectableUserAddress.toUserAddressItem(): UserAddressItem =
    UserAddressItem(
        uuid = address.uuid,
        address = address.toAddressString(),
        isSelected = isSelected,
    )

@Composable
fun UserAddress.toAddressString(): String {
    val houseShort = stringResource(Res.string.msg_address_house_short)
    val flatShort = stringResource(Res.string.msg_address_flat_short)
    val entranceShort = stringResource(Res.string.msg_address_entrance_short)
    val floorShort = stringResource(Res.string.msg_address_floor_short)
    return street +
            listOf(houseShort, house).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(flatShort, flat).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(entrance, entranceShort).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(floor, floorShort).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(comment).toStringOrEmpty(ADDRESS_DIVIDER)
}


fun List<Any?>.toStringOrEmpty(divider: String): String {
    val isAnyNullOrEmpty =
        any { part ->
            part == null || part.toString().isEmpty()
        }
    return if (isAnyNullOrEmpty) {
        ""
    } else {
        "$divider ${joinToString("")}"
    }
}
