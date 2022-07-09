package com.bunbeauty.shared.data.di

import dev.gitlive.firebase.Firebase
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {
    single {
        Json {
            isLenient = false
            ignoreUnknownKeys = true
        }
    }
      single {
          HttpClient {
              install(JsonFeature) {
                  serializer = KotlinxSerializer(kotlinx.serialization.json.Json{
                      prettyPrint = true
                      isLenient = true
                      ignoreUnknownKeys = true
                      encodeDefaults = true
                  })
              }

              install(WebSockets) {
                  pingInterval = 10
              }

              install(Logging) {
                  logger = object : Logger {
                      override fun log(message: String) {
                          //Log.v("Logger Ktor =>", message)
                      }
                  }
                  level = LogLevel.ALL
              }

              install(ResponseObserver) {
                  onResponse { response ->
                      //Log.d("HTTP status:", "${response.status.value}")
                  }
              }

              install(DefaultRequest) {
                  host = "food-delivery-api-bunbeauty.herokuapp.com"
                  header(HttpHeaders.ContentType, ContentType.Application.Json)
                  url {
                      protocol = URLProtocol.HTTPS
                  }
              }
          }
      }
    single {
        Firebase
    }
}