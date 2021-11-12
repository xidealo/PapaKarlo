package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Logger.TEST_TAG
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    fun refreshData(mainInteractor: IMainInteractor) {
        mainInteractor.refreshData()
    }

    fun connectWS() {
        val client = HttpClient(OkHttp) {
            install(WebSockets)
        }

        viewModelScope.launch {
            logD(TEST_TAG, "connectWS")
            try {
                client.webSocket(
                    HttpMethod.Get,
                    host = "food-delivery-api-bunbeauty.herokuapp.com",
                    port = 8080,
                    path = "/topic/orders"
                ) {
                    logD(TEST_TAG, "webSocket /topic/orders")
                    while (true) {
                        val othersMessage = incoming.receive() as? Frame.Text ?: continue
                        logD(TEST_TAG, "/topic/orders " + othersMessage.readText())
                        val myMessage = readLine()
                        if (myMessage != null) {
                            send(myMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                logD(TEST_TAG, "ex " + e.message)
            }
        }
    }
}