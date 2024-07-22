package com.bunbeauty.shared.data.dao.link

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.LinkEntity

class LinkDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ILinkDao {

    private val linkEntityQueries = foodDeliveryDatabase.linkEntityQueries

    override suspend fun insertLinkList(linkList: List<LinkEntity>) {
        linkEntityQueries.transaction {
            linkList.onEach { link ->
                linkEntityQueries.insertLink(
                    uuid = link.uuid,
                    type = link.type,
                    value_ = link.value_
                )
            }
        }
    }

    override suspend fun getLinkList(): List<LinkEntity> {
        return linkEntityQueries.getLinkList().executeAsList()
    }
}
