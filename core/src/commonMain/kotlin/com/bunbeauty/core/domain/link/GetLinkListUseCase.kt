package com.bunbeauty.core.domain.link

import com.bunbeauty.core.model.link.Link

expect class GetLinkListUseCase {
    suspend operator fun invoke(): List<Link>
}