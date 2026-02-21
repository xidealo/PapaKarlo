package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.LinkRepo
import com.bunbeauty.core.extension.getNullableResult
import com.bunbeauty.core.model.link.Link
import com.bunbeauty.shared.data.dao.link.ILinkDao
import com.bunbeauty.shared.data.mapper.link.LinkMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector

class LinkRepository(
    private val networkConnector: NetworkConnector,
    private val linkMapper: LinkMapper,
    private val linkDao: ILinkDao,
) : LinkRepo {
    private var linkListCache: List<Link>? = null

    override suspend fun getLinkList(): List<Link> {
        val cache = linkListCache
        return if (cache == null) {
            val linkList = getRemoteLinkList()
            if (linkList == null) {
                getLocalLinkList()
            } else {
                saveLinkListLocally(linkList)
                linkListCache = linkList
                linkList
            }
        } else {
            cache
        }
    }

    suspend fun getRemoteLinkList(): List<Link>? =
        networkConnector
            .getLinkList()
            .getNullableResult { linkServerList ->
                linkServerList.results.map(linkMapper::toLink)
            }

    suspend fun getLocalLinkList(): List<Link> = linkDao.getLinkList().map(linkMapper::toLink)

    suspend fun saveLinkListLocally(linkList: List<Link>) {
        linkDao.insertLinkList(
            linkList.map(linkMapper::toLinkEntity),
        )
    }
}
