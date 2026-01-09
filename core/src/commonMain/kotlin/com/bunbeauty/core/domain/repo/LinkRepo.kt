package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.link.Link

interface LinkRepo {
    suspend fun getLinkList(): List<Link>
}
