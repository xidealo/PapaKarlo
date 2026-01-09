package com.bunbeauty.shared.domain.repo

import com.bunbeauty.core.model.link.Link

interface LinkRepo {
    suspend fun getLinkList(): List<Link>
}
