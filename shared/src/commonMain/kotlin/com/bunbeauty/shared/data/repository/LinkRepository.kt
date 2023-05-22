package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.link.ILinkDao
import com.bunbeauty.shared.data.mapper.link.LinkMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.repo.LinkRepo
import com.bunbeauty.shared.extension.getNullableResult

class LinkRepository(
    private val networkConnector: NetworkConnector,
    private val linkMapper: LinkMapper,
    private val linkDao: ILinkDao,
): LinkRepo {

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

    suspend fun getRemoteLinkList(): List<Link>? {
        return networkConnector.getLinkList()
            .getNullableResult { linkServerList ->
                linkServerList.results.map(linkMapper::toLink)
            }
    }

    suspend fun getLocalLinkList(): List<Link> {
        return linkDao.getLinkList().map(linkMapper::toLink)
    }

    suspend fun saveLinkListLocally(linkList: List<Link>) {
        linkDao.insertLinkList(
            linkList.map(linkMapper::toLinkEntity)
        )
    }


}