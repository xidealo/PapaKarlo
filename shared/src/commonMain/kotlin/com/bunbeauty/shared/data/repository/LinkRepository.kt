package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.link.ILinkDao
import com.bunbeauty.shared.data.mapper.link.LinkMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.LinkServer
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.repo.LinkRepo

class LinkRepository(
    private val networkConnector: NetworkConnector,
    private val linkMapper: LinkMapper,
    private val linkDao: ILinkDao
) : CacheListRepository<Link>(), LinkRepo {

    override val tag: String = "LINK_TAG"

    override suspend fun getLinkList(): List<Link> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getLinkList,
            onLocalRequest = ::getLocalLinkList,
            onSaveLocally = ::saveLinkListLocally,
            serverToDomainModel = linkMapper::toLink
        )
    }

    private suspend fun getLocalLinkList(): List<Link> {
        return linkDao.getLinkList().map(linkMapper::toLink)
    }

    private suspend fun saveLinkListLocally(linkList: List<LinkServer>) {
        linkDao.insertLinkList(
            linkList.map(linkMapper::toLinkEntity)
        )
    }
}
