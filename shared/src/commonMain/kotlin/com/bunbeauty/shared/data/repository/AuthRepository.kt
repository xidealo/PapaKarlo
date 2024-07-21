package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.CodeRequestServer
import com.bunbeauty.shared.data.network.model.login.CodeServer
import com.bunbeauty.shared.domain.model.AuthResponse
import com.bunbeauty.shared.domain.repo.AuthRepo
import com.bunbeauty.shared.extension.dataOrNull
import com.bunbeauty.shared.extension.isSuccess

class AuthRepository(private val networkConnector: NetworkConnector) : AuthRepo {

    private var authSessionUuid: String? = null

    override suspend fun requestCode(phoneNumber: String): Boolean {
        val response = networkConnector.postCodeRequest(CodeRequestServer(phoneNumber = phoneNumber))
        authSessionUuid = response.dataOrNull()?.uuid

        return response.isSuccess
    }

    override suspend fun resendCode(): Boolean {
        return authSessionUuid?.let { uuid ->
            networkConnector.putCodeResend(uuid = uuid).isSuccess
        } ?: false
    }

    override suspend fun checkCode(code: String): AuthResponse? {
        return authSessionUuid?.let { uuid ->
            networkConnector.putCodeCheck(
                code = CodeServer(code = code),
                uuid = uuid
            ).dataOrNull()?.let { authResponse ->
                AuthResponse(
                    token = authResponse.token,
                    userUuid = authResponse.userUuid
                )
            }
        }
    }
}
