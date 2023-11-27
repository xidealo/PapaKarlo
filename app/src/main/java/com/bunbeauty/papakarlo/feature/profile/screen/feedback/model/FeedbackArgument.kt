package com.bunbeauty.papakarlo.feature.profile.screen.feedback.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FeedbackArgument(
    val linkList: List<LinkUI>
) : Parcelable
