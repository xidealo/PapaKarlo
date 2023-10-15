package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.CodeRequestServer
import com.bunbeauty.shared.data.network.model.login.CodeServer
import com.bunbeauty.shared.domain.model.AuthResponse
import com.bunbeauty.shared.domain.repo.AuthRepo
import com.bunbeauty.shared.extension.dataOrNull

class AuthRepository(private val networkConnector: NetworkConnector) : AuthRepo {

    private var authSessionUuid: String? = null

    override suspend fun requestCode(phoneNumber: String) {
        val authSession = networkConnector.postCodeRequest(CodeRequestServer(phoneNumber = phoneNumber)).dataOrNull()
        authSessionUuid = authSession?.uuid
    }

    override suspend fun resendCode() {
        authSessionUuid?.let { uuid ->
            networkConnector.putCodeResend(uuid = uuid)
        }
    }

    override suspend fun checkCode(code: String): AuthResponse? {
        return authSessionUuid?.let { uuid ->
            networkConnector.putCodeCheck(
                code = CodeServer(code = code),
                uuid = uuid
            ).dataOrNull()?.let { authResponse ->
                AuthResponse(
                    token = authResponse.token,
                    userUuid = authResponse.userUuid,
                )
            }
        }
    }

}