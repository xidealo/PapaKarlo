package com.bunbeauty.shared.data.mapper.link

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.shared.data.network.model.LinkServer
import com.bunbeauty.shared.db.LinkEntity

class LinkMapper {
    fun toLink(linkServer: LinkServer): Link {
        val type =
            LinkType.entries.find { linkType ->
                linkType.name == linkServer.type
            } ?: LinkType.UNKNOWN

        return Link(
            uuid = linkServer.uuid,
            type = type,
            linkValue = linkServer.value,
        )
    }

    fun toLink(linkEntity: LinkEntity): Link {
        val type =
            LinkType.entries.find { linkType ->
                linkType.name == linkEntity.type
            } ?: LinkType.UNKNOWN

        return Link(
            uuid = linkEntity.uuid,
            type = type,
            linkValue = linkEntity.value_,
        )
    }

    fun toLinkEntity(link: Link): LinkEntity =
        LinkEntity(
            uuid = link.uuid,
            type = link.type.name,
            value_ = link.linkValue,
        )
}
