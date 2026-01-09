package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.CodeRequestServer
import com.bunbeauty.shared.data.network.model.login.CodeServer
import com.bunbeauty.core.domain.repo.AuthRepo
import com.bunbeauty.core.extension.dataOrNull
import com.bunbeauty.core.extension.isSuccess
import com.bunbeauty.core.model.AuthResponse

class AuthRepository(
    private val networkConnector: NetworkConnector,
) : AuthRepo {
    private var authSessionUuid: String? = null

    override suspend fun requestCode(phoneNumber: String): Boolean {
        val response = networkConnector.postCodeRequest(CodeRequestServer(phoneNumber = phoneNumber))
        authSessionUuid = response.dataOrNull()?.uuid

        return response.isSuccess
    }

    override suspend fun resendCode(): Boolean =
        authSessionUuid?.let { uuid ->
            networkConnector.putCodeResend(uuid = uuid).isSuccess
        } ?: false

    override suspend fun checkCode(code: String): AuthResponse? =
        authSessionUuid?.let { uuid ->
            networkConnector
                .putCodeCheck(
                    code = CodeServer(code = code),
                    uuid = uuid,
                ).dataOrNull()
                ?.let { authResponse ->
                    AuthResponse(
                        token = authResponse.token,
                        userUuid = authResponse.userUuid,
                    )
                }
        }
}
