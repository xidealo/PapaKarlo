package com.bunbeauty.core.domain.link

import com.bunbeauty.core.domain.repo.LinkRepo
import com.bunbeauty.core.model.link.Link

actual class GetLinkUseCase actual constructor(
    private val linkRepo: LinkRepo,
) {
    actual suspend operator fun invoke(): Link? = null
}
