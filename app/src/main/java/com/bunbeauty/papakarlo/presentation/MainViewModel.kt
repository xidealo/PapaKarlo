package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    fun refreshData(mainInteractor: IMainInteractor) {
        mainInteractor.refreshData()
    }

//    fun connectWS() {
//        val client = HttpClient(OkHttp) {
//            install(WebSockets)
//        }
//
//        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIiwiaXNzIjoiRm9vZERlbGl2ZXJ5QXBpIiwidXNlclV1aWQiOiIwY2FjNzBkOC04ZDg1LTRjNTQtOWYwNS03NWI2M2U3ZjUyNjYiLCJ1c2VyUm9sZSI6Im1hbmFnZXIifQ.FmAGUaAWVXQ1pnrwG_58PmvGmLZpWe_GXMkCcNf53ks"
//        viewModelScope.launch {
//            logD(TEST_TAG, "connectWS")
//            try {
//                client.webSocket(
//                    HttpMethod.Get,
//                    host = "food-delivery-api-bunbeauty.herokuapp.com",
//                    path = "user/order/subscribe",
//                    request = {
//                        header("Authorization", "Bearer $token")
//                        parameter("cafeUuid", "2efe19e0-4324-4c5f-b214-c5cc6da2dacf")
//                    }
//                ) {
//                    logD(TEST_TAG, "webSocket /order/subscribe")
//                    while (true) {
//                        val othersMessage = incoming.receive() as? Frame.Text ?: continue
//                        logD(TEST_TAG, "income " + othersMessage.readText())
//                    }
//                }
//            } catch (e: Exception) {
//                logD(TEST_TAG, "ex " + e.message)
//            }
//        }
//    }
}