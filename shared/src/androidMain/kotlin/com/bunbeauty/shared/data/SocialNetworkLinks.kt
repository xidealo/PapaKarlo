package com.bunbeauty.shared.data

import com.bunbeauty.shared.BuildConfig
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_INSTAGRAM_LINK
import com.bunbeauty.shared.Constants.PAPA_KARLO_PLAY_MARKET_LINK
import com.bunbeauty.shared.Constants.PAPA_KARLO_VK_LINK
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.Constants.YULIAR_PLAY_MARKET_LINK
import com.bunbeauty.shared.Constants.YULIAR_VK_LINK
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException
import com.bunbeauty.shared.domain.model.SocialNetworkLinks

actual val socialNetworkLinks: SocialNetworkLinks = when (BuildConfig.FLAVOR) {
    PAPA_KARLO_FLAVOR_NAME -> SocialNetworkLinks(
        vkLink = PAPA_KARLO_VK_LINK,
        instagramLink = PAPA_KARLO_INSTAGRAM_LINK,
        googlePlayLink = PAPA_KARLO_PLAY_MARKET_LINK,
        appStoreLink = null,
    )
    YULIAR_FLAVOR_NAME -> SocialNetworkLinks(
        vkLink = YULIAR_VK_LINK,
        instagramLink = null,
        googlePlayLink = YULIAR_PLAY_MARKET_LINK,
        appStoreLink = null,
    )
    else -> throw UnknownFlavorException()
}