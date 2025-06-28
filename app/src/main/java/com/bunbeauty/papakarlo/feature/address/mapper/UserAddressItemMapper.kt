package com.bunbeauty.papakarlo.feature.address.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.address.model.UserAddressItem
import com.bunbeauty.shared.Constants.ADDRESS_DIVIDER
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.address.UserAddressWithCity

@Composable
fun SelectableUserAddress.toUserAddressItem(): UserAddressItem {
    return UserAddressItem(
        uuid = address.uuid,
        address = address.toAddressString(),
        isSelected = isSelected
    )
}

@Composable
fun UserAddress.toAddressString(): String {
    val houseShort = stringResource(R.string.msg_address_house_short)
    val flatShort = stringResource(R.string.msg_address_flat_short)
    val entranceShort = stringResource(R.string.msg_address_entrance_short)
    val floorShort = stringResource(R.string.msg_address_floor_short)
    return street +
            listOf(houseShort, house).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(flatShort, flat).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(entrance, entranceShort).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(floor, floorShort).toStringOrEmpty(ADDRESS_DIVIDER) +
            listOf(comment).toStringOrEmpty(ADDRESS_DIVIDER)
}

@Composable
fun UserAddressWithCity.toAddressString(): String {
    return city + ADDRESS_DIVIDER + userAddress?.toAddressString()
}

private fun List<Any?>.toStringOrEmpty(divider: String): String {
    val isAnyNullOrEmpty = any { part ->
        part == null || part.toString().isEmpty()
    }
    return if (isAnyNullOrEmpty) {
        ""
    } else {
        "$divider ${joinToString("")}"
    }
}
