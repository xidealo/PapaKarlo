package com.bunbeauty.shared.domain.feature.link

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.domain.repo.LinkRepo

class GetLinkUseCase(private val linkRepo: LinkRepo) {

    suspend operator fun invoke(linkType: LinkType): Link? {
        return linkRepo.getLinkList().find { link ->
            link.type == linkType
        }
    }
}
