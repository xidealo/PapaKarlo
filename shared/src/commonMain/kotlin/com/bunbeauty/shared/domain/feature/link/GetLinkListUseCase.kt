package com.bunbeauty.shared.domain.feature.link

import com.bunbeauty.core.model.link.Link

expect class GetLinkListUseCase {
    suspend operator fun invoke(): List<Link>
}
