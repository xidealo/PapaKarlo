package com.bunbeauty.domain.auth

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthUtil @Inject constructor() : IAuthUtil {

    override val isAuthorize: Boolean
        get() = userUuid != null

    override val userUuid: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    override val userPhone: String?
        get() = FirebaseAuth.getInstance().currentUser?.phoneNumber
}