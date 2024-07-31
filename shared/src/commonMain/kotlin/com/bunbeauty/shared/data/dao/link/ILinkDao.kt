package com.bunbeauty.shared.data.dao.link

import com.bunbeauty.shared.db.LinkEntity

interface ILinkDao {

    suspend fun insertLinkList(linkList: List<LinkEntity>)

    suspend fun getLinkList(): List<LinkEntity>
}
