package com.bunbeauty.data.mapper.adapter

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.adapter.CafeAdapterModel
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.model.local.cafe.Cafe
import com.bunbeauty.domain.util.cafe.ICafeUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import org.joda.time.DateTime
import javax.inject.Inject

class CafeAdapterMapper @Inject constructor(
    private val iStringHelper: IStringHelper,
    private val iCafeUtil: ICafeUtil
) : Mapper<CafeAdapterModel, Cafe> {

    override fun from(e: Cafe): CafeAdapterModel {
        return CafeAdapterModel(
            uuid = e.cafeEntity.id,
            address = iStringHelper.toString(e.address),
            workingHours = iStringHelper.toStringWorkingHours(e.cafeEntity),
            workingTimeMessage = iCafeUtil.getIsClosedMessage(
                e.cafeEntity.fromTime,
                e.cafeEntity.toTime,
                DateTime.now()
            ),
            workingTimeMessageColor = iCafeUtil.getIsClosedColor(
                e.cafeEntity.fromTime,
                e.cafeEntity.toTime,
                DateTime.now()
            ),
            phone = e.cafeEntity.phone,
            coordinate = e.cafeEntity.coordinate
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: CafeAdapterModel): Cafe {
        return Cafe()
    }
}