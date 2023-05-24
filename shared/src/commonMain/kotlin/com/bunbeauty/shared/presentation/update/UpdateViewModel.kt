package com.bunbeauty.shared.presentation.update

import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UpdateViewModel(
    private val getLinkListUseCase: GetLinkListUseCase,
) : SharedViewModel() {

    private val mutableUiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Loading)
    val uiState = mutableUiState.asStateFlow()

    fun updateGooglePlayLink() {
        sharedScope.launchSafe(
            block = {
                mutableUiState.value = getLinkListUseCase.invoke().find { link ->
                    link.type == LinkType.GOOGLE_PLAY
                }?.let { link ->
                    UpdateUiState.Success(link)
                } ?: UpdateUiState.Error
            },
            onError = {
                mutableUiState.value = UpdateUiState.Error
            }
        )
    }

}