package com.bunbeauty.shared.data.mapper.link

import com.bunbeauty.shared.data.network.model.LinkServer
import com.bunbeauty.shared.db.LinkEntity
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType

class LinkMapper {

    fun toLink(linkServer: LinkServer): Link {
        val type = LinkType.values().find { linkType ->
            linkType.name == linkServer.type
        } ?: LinkType.UNKNOWN

        return Link(
            uuid = linkServer.uuid,
            type = type,
            linkValue = linkServer.value,
        )
    }

    fun toLink(linkEntity: LinkEntity): Link {
        val type = LinkType.values().find { linkType ->
            linkType.name == linkEntity.type
        } ?: LinkType.UNKNOWN

        return Link(
            uuid = linkEntity.uuid,
            type = type,
            linkValue = linkEntity.value_,
        )
    }

    fun toLinkEntity(link: Link): LinkEntity {
        return LinkEntity(
            uuid = link.uuid,
            type = link.type.name,
            value_ = link.linkValue,
        )
    }
}