package com.bunbeauty.shared.domain.feature.link

import com.bunbeauty.shared.domain.model.link.Link

expect class GetLinkListUseCase {
    suspend operator fun invoke(): List<Link>
}
