package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OneLineActionViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo
) : BaseViewModel() {

    fun updateEmail(previousEmail: String, email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            if (previousEmail == email) {
                goBack()
            } else {
                withContext(Dispatchers.Default) {
                    val user = userRepo.getUserByUuid(dataStoreRepo.userUuid.first()) ?: return@withContext
                    //user.email = email
                    //userRepo.update(user)
                }
                goBack()
            }
        }
    }

}