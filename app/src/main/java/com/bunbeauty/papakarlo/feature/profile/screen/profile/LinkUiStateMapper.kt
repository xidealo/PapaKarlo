package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.LinkUI
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType

class LinkUiStateMapper {

    fun map(linkList: List<Link>): List<LinkUI> {
        return linkList.map { link ->
            LinkUI(
                uuid = link.uuid,
                labelId = when (link.type) {
                    LinkType.VKONTAKTE -> R.string.action_feedback_vk
                    LinkType.INSTAGRAM -> R.string.action_feedback_instagram
                    LinkType.GOOGLE_PLAY -> R.string.action_feedback_play_market
                    LinkType.APP_STORE -> R.string.action_feedback_app_store
                },
                iconId = when (link.type) {
                    LinkType.VKONTAKTE -> R.drawable.ic_vk
                    LinkType.INSTAGRAM -> R.drawable.ic_instagram
                    LinkType.GOOGLE_PLAY -> R.drawable.ic_google_play
                    LinkType.APP_STORE -> R.drawable.ic_app_store
                },
                value = link.value,
            )
        }
    }
}
