package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.common.view_model.Action

sealed class MenuAction : Action {
    data class GoToSelectedItem(val uuid: String, val name: String) : MenuAction()
}
