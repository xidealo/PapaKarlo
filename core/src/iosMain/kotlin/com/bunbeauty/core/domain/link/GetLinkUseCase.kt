package com.bunbeauty.core.domain.link

import com.bunbeauty.core.domain.repo.LinkRepo
import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType

actual class GetLinkUseCase(
    private val linkRepo: LinkRepo,
) {
    actual suspend operator fun invoke(): Link? = linkRepo.getLinkList().find { link ->
        link.type == LinkType.APP_STORE
    }
}