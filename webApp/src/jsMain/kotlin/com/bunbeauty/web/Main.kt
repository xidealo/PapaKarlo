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
import com.bunbeauty.shared.resolveWebFlavor
import com.bunbeauty.shared.ui.screen.main.MainScreen
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.koin.dsl.module

// Browser tab title per brand (index.html <title> is static, so we set it at
// runtime). Names match the Android app_name of each flavor.
private fun webTitle(flavor: String): String {
    val name =
        when (flavor) {
            "papakarlo" -> "Папа Карло"
            "yuliar" -> "ЮлиАр"
            "djan" -> "Шашлык Джан"
            "gustopub" -> "Густо Паб"
            "tandirhouse" -> "Тандыр Хаус"
            "vkuskavkaza" -> "Вкус Кавказа"
            "estpoest" -> "#Есть Поесть"
            "legenda" -> "Легенда"
            "usadba" -> "Усадьба"
            "emoji" -> "Эмодзи"
            "limonad" -> "Лимонад"
            "taverna" -> "Таверна"
            "voljane" -> "Волжане"
            "bereg" -> "Берег"
            "mimino" -> "Мимино"
            else -> null
        }
    return if (name != null) "$name — доставка еды" else "Доставка еды"
}

// Sets the browser tab icon (favicon) to the brand logo bundled in
// composeResources. index.html has no <link rel="icon">, so we create/update it
// at runtime. Modern browsers accept .webp favicons.
private fun applyFavicon(flavor: String) {
    val href = "composeResources/papakarlo.designsystem.generated.resources/drawable/logo_small_$flavor.webp"
    val link =
        document.querySelector("link[rel='icon']")
            ?: document.createElement("link").also { element ->
                element.setAttribute("rel", "icon")
                document.head?.appendChild(element)
            }
    link.setAttribute("type", "image/webp")
    link.setAttribute("href", href)
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // The backend (amvera) does not send CORS headers, so on the web we route
    // requests through the dev-server proxy by talking to the current page origin.
    // The webpack proxy (webApp/webpack.config.d/proxy.js) forwards them to amvera.
    NetworkConfig.protocol = window.location.protocol.trimEnd(':')
    NetworkConfig.host = window.location.hostname
    NetworkConfig.port = window.location.port.toIntOrNull()

    // One build/host serves many cafes: the brand is picked from the domain.
    // Must match the flavor Koin resolves in PlatformModule (also resolveWebFlavor).
    val flavor = resolveWebFlavor()

    // The index.html <title>/favicon are static, but the shown brand is chosen at
    // runtime, so we set the browser tab title and icon from the current flavor.
    document.title = webTitle(flavor)
    applyFavicon(flavor)

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
                        add(YandexImageProxyMapper())
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

            FoodDeliveryTheme(flavor = flavor) {
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

private const val YANDEX_STORAGE_URL = "https://fooddelivery-s3-test.storage.yandexcloud.net"

// Some product photos live on Yandex Object Storage, which (like Firebase) does
// not send CORS headers, so direct browser fetches are blocked. We rewrite those
// URLs to the same-origin /yandex-img proxy path, forwarded to Yandex Storage by
// the dev-server proxy (locally) and netlify.toml (in production).
private class YandexImageProxyMapper : Mapper<String, String> {
    override fun map(data: String, options: Options): String? =
        if (data.startsWith(YANDEX_STORAGE_URL)) {
            window.location.origin + "/yandex-img" + data.removePrefix(YANDEX_STORAGE_URL)
        } else {
            null
        }
}
