package com.bunbeauty.core.domain.link

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.core.domain.repo.LinkRepo

expect class GetLinkUseCase(linkRepo: LinkRepo) {
    suspend operator fun invoke(): Link?
}