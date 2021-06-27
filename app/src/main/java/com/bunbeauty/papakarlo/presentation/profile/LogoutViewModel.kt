package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.LogoutBottomSheetDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo
): BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            dataStoreRepo.saveUserId("")
            router.navigate(LogoutBottomSheetDirections.toProfileFragment())
        }
    }
    fun cancel(){
        router.navigateUp()
    }
}