package com.bunbeauty.shared.data.network

/**
 * Centralized network configuration.
 *
 * Can be overridden before [com.bunbeauty.shared.di.initKoin] is called.
 *
 * On the web the backend does not send CORS headers, so during local development
 * requests are routed through the webpack-dev-server proxy: the browser talks to
 * its own origin (e.g. http://localhost:8080) and the dev server forwards the
 * calls to the real backend. That is why the web entry point points these values
 * at the current page origin instead of the amvera host directly.
 */
object NetworkConfig {
    var protocol: String = "https"
    var host: String = "fooddelivery-xidealo.amvera.io"
    var port: Int? = null
}
