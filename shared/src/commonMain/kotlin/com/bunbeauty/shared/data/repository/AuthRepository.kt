package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.CodeRequestServer
import com.bunbeauty.shared.data.network.model.login.CodeServer
import com.bunbeauty.shared.domain.model.AuthResponseNew
import com.bunbeauty.shared.domain.model.CodeResponse
import com.bunbeauty.shared.domain.repo.AuthRepo
import com.bunbeauty.shared.extension.map

class AuthRepository(private val networkConnector: NetworkConnector) : AuthRepo {

    var authSessionUuid: String? = null

    override suspend fun requestCode(phoneNumber: String): CodeResponse {
        val apiResult = networkConnector.postCodeRequest(CodeRequestServer(phoneNumber = phoneNumber))
        return apiResult.map(
            onSuccess = { authSession ->
                authSessionUuid = authSession?.uuid

                if (authSession?.uuid == null) {
                    CodeResponse.SOMETHING_WENT_WRONG_ERROR
                } else {
                    CodeResponse.SUCCESS
                }
            },
            onError = { apiError ->
                when (apiError.code) {
                    800 -> CodeResponse.TOO_MANY_REQUESTS_ERROR
                    else -> CodeResponse.SOMETHING_WENT_WRONG_ERROR
                }
            }
        )
    }

    override suspend fun resendCode(): CodeResponse {
        return authSessionUuid?.let { uuid ->
            networkConnector.putCodeResend(uuid = uuid).map(
                onSuccess = {
                    CodeResponse.SUCCESS
                },
                onError = { apiError ->
                    when (apiError.code) {
                        800 -> CodeResponse.TOO_MANY_REQUESTS_ERROR
                        else -> CodeResponse.SOMETHING_WENT_WRONG_ERROR
                    }
                }
            )
        } ?: CodeResponse.SOMETHING_WENT_WRONG_ERROR
    }

    override suspend fun checkCode(code: String): AuthResponseNew {
        return authSessionUuid?.let { uuid ->
            networkConnector.putCodeCheck(
                code = CodeServer(code = code),
                uuid = uuid
            ).map(
                onSuccess = { authResponseServer ->
                    authResponseServer?.let {
                        AuthResponseNew.Success(
                            token = authResponseServer.token,
                            userUuid = authResponseServer.userUuid,
                        )
                    } ?: AuthResponseNew.Error.SOMETHING_WENT_WRONG_ERROR
                },
                onError = { apiError ->
                    when (apiError.code) {
                        801 -> AuthResponseNew.Error.NO_ATTEMPTS_ERROR
                        802 -> AuthResponseNew.Error.INVALID_CODE_ERROR
                        803 -> AuthResponseNew.Error.AUTH_SESSION_TIMEOUT_ERROR
                        else -> AuthResponseNew.Error.SOMETHING_WENT_WRONG_ERROR
                    }
                },
            )
        } ?: AuthResponseNew.Error.SOMETHING_WENT_WRONG_ERROR
    }

}