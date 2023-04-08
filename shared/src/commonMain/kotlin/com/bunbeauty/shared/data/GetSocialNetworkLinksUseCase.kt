package com.bunbeauty.shared.data

import com.bunbeauty.shared.domain.model.SocialNetworkLinks

expect class GetSocialNetworkLinksUseCase{
    fun invoke(): SocialNetworkLinks
}