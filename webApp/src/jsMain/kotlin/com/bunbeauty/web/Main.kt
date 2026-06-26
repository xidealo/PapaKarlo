package com.bunbeauty.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.map.Mapper
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.Options
import coil3.request.crossfade
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.shared.data.network.NetworkConfig
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.di.initKoin
import com.bunbeauty.shared.ui.screen.main.MainScreen
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // The backend (amvera) does not send CORS headers, so on the web we route
    // requests through the dev-server proxy by talking to the current page origin.
    // The webpack proxy (webApp/webpack.config.d/proxy.js) forwards them to amvera.
    NetworkConfig.protocol = window.location.protocol.trimEnd(':')
    NetworkConfig.host = window.location.hostname
    NetworkConfig.port = window.location.port.toIntOrNull()

    CoroutineScope(Dispatchers.Main).launch {
        val driver = initSqlDriver(FoodDeliveryDatabase.Schema).await()
        val database = FoodDeliveryDatabase(driver)

        initKoin {
            modules(
                module {
                    single { database }
                },
            )
        }

        ComposeViewport(document.body!!) {
            // On the web there is no real filesystem, so we use a memory-only
            // ImageLoader. Coil's default loader would try to set up a disk cache
            // via okio (os.tmpdir()), which is not available in the browser.
            setSingletonImageLoaderFactory { context ->
                ImageLoader
                    .Builder(context)
                    // On JS Coil does not auto-register a network fetcher (no
                    // ServiceLoader), so we add the Ktor one explicitly. Without it
                    // every http(s) image fails and only the placeholder is shown.
                    // The mapper reroutes Firebase Storage photos through the
                    // dev-server proxy because Firebase doesn't send CORS headers.
                    .components {
                        add(FirebaseImageProxyMapper())
                        add(KtorNetworkFetcherFactory())
                    }
                    // There is no disk cache on the web, so we keep a generous
                    // in-memory cache. Otherwise images get evicted quickly and
                    // reload every time they scroll back into view.
                    .memoryCache {
                        MemoryCache
                            .Builder()
                            .maxSizeBytes(256L * 1024 * 1024)
                            .build()
                    }
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .crossfade(enable = true)
                    .build()
            }

            FoodDeliveryTheme(flavor = "papakarlo") {
                MainScreen()
            }
        }
    }
}

private const val FIREBASE_STORAGE_URL = "https://firebasestorage.googleapis.com"

// Rewrites Firebase Storage photo URLs to the same-origin /firebase-img proxy
// path so the browser doesn't block them with CORS. The dev-server proxy
// (webApp/webpack.config.d/proxy.js) forwards these to Firebase Storage.
private class FirebaseImageProxyMapper : Mapper<String, String> {
    override fun map(data: String, options: Options): String? =
        if (data.startsWith(FIREBASE_STORAGE_URL)) {
            window.location.origin + "/firebase-img" + data.removePrefix(FIREBASE_STORAGE_URL)
        } else {
            null
        }
}
