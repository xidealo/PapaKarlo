package com.bunbeauty.domain.worker

@Deprecated("replaced on request on screen")
interface IUserWorkerUtil {

    fun refreshUser(token: String)
    suspend fun refreshUserBlocking(token: String)
    fun cancelRefreshUser()
}