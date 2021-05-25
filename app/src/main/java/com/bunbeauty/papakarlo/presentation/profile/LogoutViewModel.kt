package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.profile.LogoutBottomSheetDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper
): BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            dataStoreHelper.saveUserId("")
            router.navigate(LogoutBottomSheetDirections.toProfileFragment())
        }
    }
    fun cancel(){
        router.navigateUp()
    }
}