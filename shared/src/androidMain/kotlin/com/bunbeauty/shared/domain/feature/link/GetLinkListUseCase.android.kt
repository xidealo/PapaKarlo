package com.bunbeauty.shared.domain.feature.link

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.shared.domain.repo.LinkRepo

actual class GetLinkListUseCase(
    private val linkRepo: LinkRepo,
) {
    actual suspend operator fun invoke(): List<Link> =
        linkRepo.getLinkList().filter { link ->
            link.type != LinkType.APP_STORE
        }
}
