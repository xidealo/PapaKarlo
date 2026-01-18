package com.bunbeauty.core.domain.link

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.core.domain.repo.LinkRepo

class GetLinkUseCase(
    private val linkRepo: LinkRepo,
) {
    suspend operator fun invoke(linkType: LinkType): Link? =
        linkRepo.getLinkList().find { link ->
            link.type == linkType
        }
}
