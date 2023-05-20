package com.bunbeauty.shared.data.mapper.link

import com.bunbeauty.shared.data.network.model.LinkServer
import com.bunbeauty.shared.db.LinkEntity
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType

class LinkMapper {

    fun toLink(linkServer: LinkServer): Link? {
        val type = LinkType.values().find { linkType ->
            linkType.name == linkServer.type
        } ?: return null

        return Link(
            uuid = linkServer.uuid,
            type = type,
            value = linkServer.value,
        )
    }

    fun toLink(linkEntity: LinkEntity): Link? {
        val type = LinkType.values().find { linkType ->
            linkType.name == linkEntity.type
        } ?: return null

        return Link(
            uuid = linkEntity.uuid,
            type = type,
            value = linkEntity.value_,
        )
    }

    fun toLinkEntity(link: Link): LinkEntity {
        return LinkEntity(
            uuid = link.uuid,
            type = link.type.name,
            value_ = link.value,
        )
    }
}