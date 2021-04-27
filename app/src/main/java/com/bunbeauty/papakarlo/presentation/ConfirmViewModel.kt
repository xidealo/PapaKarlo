package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ConfirmFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper
) : ToolbarViewModel() {

    fun saveUserId(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.saveUserId(userId)
            router.navigate(ConfirmFragmentDirections.toProfileFragment())
        }
    }

}