package com.bunbeauty.domain.worker

interface ICategoryWorkerUtil {

    suspend fun refreshCategoryList()
}