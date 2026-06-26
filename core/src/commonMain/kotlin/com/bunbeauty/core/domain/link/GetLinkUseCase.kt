package com.bunbeauty.core.domain.link

import com.bunbeauty.core.domain.repo.LinkRepo
import com.bunbeauty.core.model.link.Link

expect class GetLinkUseCase(linkRepo: LinkRepo) {
    suspend operator fun invoke(): Link?
}