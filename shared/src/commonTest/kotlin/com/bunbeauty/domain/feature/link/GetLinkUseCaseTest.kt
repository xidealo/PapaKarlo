package com.bunbeauty.domain.feature.link

import com.bunbeauty.shared.domain.feature.link.GetLinkUseCase
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.domain.repo.LinkRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetLinkUseCaseTest {

    private val linkRepo: LinkRepo = mock()
    private val getLinkUseCase: GetLinkUseCase = GetLinkUseCase(
        linkRepo = linkRepo
    )

    @Test
    fun `returns link when link with matching type exists`() = runTest {
        // Given
        val linkType = LinkType.GOOGLE_PLAY
        val expectedLink = Link(
            type = linkType,
            uuid = "uuid",
            linkValue = "https://promo.example.com"
        )
        everySuspend { linkRepo.getLinkList() } returns listOf(expectedLink)

        // When
        val result = getLinkUseCase.invoke(linkType)

        // Then
        assertEquals(expectedLink, result)
    }

    @Test
    fun `returns null when no link with matching type exists`() = runTest {
        // Given
        val linkType = LinkType.GOOGLE_PLAY
        everySuspend { linkRepo.getLinkList() } returns emptyList()

        // When
        val result = getLinkUseCase.invoke(linkType)

        // Then
        assertNull(result)
    }
}
