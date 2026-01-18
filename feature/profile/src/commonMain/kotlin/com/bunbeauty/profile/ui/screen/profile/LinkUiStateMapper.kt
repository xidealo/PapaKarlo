package com.bunbeauty.profile.ui.screen.profile

import com.bunbeauty.core.model.link.Link
import com.bunbeauty.core.model.link.LinkType
import com.bunbeauty.profile.ui.screen.feedback.model.LinkUI
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_feedback_app_store
import papakarlo.designsystem.generated.resources.action_feedback_facebook
import papakarlo.designsystem.generated.resources.action_feedback_instagram
import papakarlo.designsystem.generated.resources.action_feedback_play_market
import papakarlo.designsystem.generated.resources.action_feedback_vk
import papakarlo.designsystem.generated.resources.ic_app_store
import papakarlo.designsystem.generated.resources.ic_facebook
import papakarlo.designsystem.generated.resources.ic_google_play
import papakarlo.designsystem.generated.resources.ic_instagram
import papakarlo.designsystem.generated.resources.ic_link
import papakarlo.designsystem.generated.resources.ic_vk

fun Link.toUI(): LinkUI =
    LinkUI(
        uuid = uuid,
        labelId =
            when (type) {
                LinkType.VKONTAKTE -> Res.string.action_feedback_vk
                LinkType.INSTAGRAM -> Res.string.action_feedback_instagram
                LinkType.GOOGLE_PLAY -> Res.string.action_feedback_play_market
                LinkType.APP_STORE -> Res.string.action_feedback_app_store
                LinkType.FACEBOOK -> Res.string.action_feedback_facebook
                LinkType.UNKNOWN -> null
            },
        iconId =
            when (type) {
                LinkType.VKONTAKTE -> Res.drawable.ic_vk
                LinkType.INSTAGRAM -> Res.drawable.ic_instagram
                LinkType.GOOGLE_PLAY -> Res.drawable.ic_google_play
                LinkType.APP_STORE -> Res.drawable.ic_app_store
                LinkType.FACEBOOK -> Res.drawable.ic_facebook
                LinkType.UNKNOWN -> Res.drawable.ic_link
            },
        value = linkValue,
    )
