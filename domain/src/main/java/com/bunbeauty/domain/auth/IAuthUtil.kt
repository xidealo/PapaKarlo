package com.bunbeauty.domain.auth

interface IAuthUtil {

    val isAuthorize: Boolean
    val userUuid: String?
    val userPhone: String?

}