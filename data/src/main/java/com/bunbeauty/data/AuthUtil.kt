package com.bunbeauty.data

import com.bunbeauty.domain.auth.IAuthUtil
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthUtil @Inject constructor() : IAuthUtil {

    override val isAuthorize: Boolean
        get() = userUuid != null

    override val userUuid: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    override val userPhone: String?
        get() = FirebaseAuth.getInstance().currentUser?.phoneNumber
}