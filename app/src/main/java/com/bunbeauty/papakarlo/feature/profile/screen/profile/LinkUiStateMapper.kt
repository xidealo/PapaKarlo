package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.model.LinkUI
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType

fun Link.toUI(): LinkUI {
    return LinkUI(
        uuid = uuid,
        labelId = when (type) {
            LinkType.VKONTAKTE -> R.string.action_feedback_vk
            LinkType.INSTAGRAM -> R.string.action_feedback_instagram
            LinkType.GOOGLE_PLAY -> R.string.action_feedback_play_market
            LinkType.APP_STORE -> R.string.action_feedback_app_store
            LinkType.FACEBOOK -> R.string.action_feedback_facebook
            LinkType.UNKNOWN -> null
        },
        iconId = when (type) {
            LinkType.VKONTAKTE -> R.drawable.ic_vk
            LinkType.INSTAGRAM -> R.drawable.ic_instagram
            LinkType.GOOGLE_PLAY -> R.drawable.ic_google_play
            LinkType.APP_STORE -> R.drawable.ic_app_store
            LinkType.FACEBOOK -> R.drawable.ic_facebook
            LinkType.UNKNOWN -> R.drawable.ic_link
        },
        value = linkValue
    )
}
