package com.bunbeauty.shared.domain.feature.link

import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.repo.LinkRepo

class GetLinkListUseCase(private val linkRepo: LinkRepo) {

    suspend operator fun invoke(): List<Link> {
        return linkRepo.getLinkList()
    }
}
