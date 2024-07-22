package com.bunbeauty.shared.presentation.update

import com.bunbeauty.shared.domain.model.link.Link

sealed interface UpdateUiState {

    object Loading : UpdateUiState
    object Error : UpdateUiState
    data class Success(val googlePayLink: Link) : UpdateUiState
}
