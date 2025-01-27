package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.link.Link

interface LinkRepo {
    suspend fun getLinkList(): List<Link>
}
