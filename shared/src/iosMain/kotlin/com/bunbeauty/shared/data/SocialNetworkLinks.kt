package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants.PAPA_KARLO_APP_STORE_LINK
import com.bunbeauty.shared.Constants.PAPA_KARLO_INSTAGRAM_LINK
import com.bunbeauty.shared.Constants.PAPA_KARLO_TARGET_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_VK_LINK
import com.bunbeauty.shared.Constants.YULIAR_APP_STORE_LINK
import com.bunbeauty.shared.Constants.YULIAR_TARGET_NAME
import com.bunbeauty.shared.Constants.YULIAR_VK_LINK
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException
import com.bunbeauty.shared.domain.model.SocialNetworkLinks

actual val socialNetworkLinks: SocialNetworkLinks = when (targetName) {
    PAPA_KARLO_TARGET_NAME -> SocialNetworkLinks(
        vkLink = PAPA_KARLO_VK_LINK,
        instagramLink = PAPA_KARLO_INSTAGRAM_LINK,
        googlePlayLink = null,
        appStoreLink = PAPA_KARLO_APP_STORE_LINK,
    )
    YULIAR_TARGET_NAME -> SocialNetworkLinks(
        vkLink = YULIAR_VK_LINK,
        instagramLink = null,
        googlePlayLink = null,
        appStoreLink = YULIAR_APP_STORE_LINK,
    )
    else -> throw UnknownFlavorException()
}