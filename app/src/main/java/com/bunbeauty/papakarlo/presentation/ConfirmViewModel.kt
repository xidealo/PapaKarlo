package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.model.user.User
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.user.UserRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ConfirmFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val userRepo: UserRepo
) : ToolbarViewModel() {

    fun createUser(userId: String, phone: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.saveUserId(userId)
            val user = User(userId = userId, phone = phone, email = email, bonus = 100)
            userRepo.insert(user)
            router.navigate(ConfirmFragmentDirections.toProfileFragment())
        }
    }

    fun getPhoneNumberDigits(phone: String): String {
        return phone.replace(Regex("\\D"), "")
    }
}