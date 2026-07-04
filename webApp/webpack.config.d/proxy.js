// The amvera backend does not send CORS headers, which blocks direct browser
// requests from http://localhost. To work around this during local development
// we proxy all API calls through webpack-dev-server (server-to-server, no CORS).
//
// Heuristic: static assets always have a file extension (.js, .wasm, .css, ...),
// while all backend endpoints are extensionless (city, cafe, menu_product, ...).
// So we proxy every extensionless path to the backend and serve the rest locally.
const BACKEND = 'https://fooddelivery-xidealo.amvera.io';
// Product photos live on Firebase Storage, which also doesn't send CORS headers,
// so browser-side image fetches are blocked too. We proxy them the same way: the
// web app rewrites every photoLink to /firebase-img/... (see Main.kt) and we
// forward that here to Firebase Storage.
const FIREBASE_STORAGE = 'https://firebasestorage.googleapis.com';
// Some product photos live on Yandex Object Storage, which also doesn't send
// CORS headers. Same treatment: the web app rewrites those photoLinks to
// /yandex-img/... (see Main.kt) and we forward them here to Yandex Storage.
const YANDEX_STORAGE = 'https://fooddelivery-s3-test.storage.yandexcloud.net';

config.devServer = config.devServer || {};
config.devServer.proxy = [
    {
        context: ['/firebase-img'],
        target: FIREBASE_STORAGE,
        changeOrigin: true,
        secure: true,
        pathRewrite: { '^/firebase-img': '' },
        // Firebase Storage responds with "cache-control: private, max-age=0",
        // so the browser refetches every photo on each scroll. There is no disk
        // cache on the web, so we make the browser cache the images by rewriting
        // the cache headers (http-proxy-middleware v2 uses the onProxyRes hook).
        onProxyRes: (proxyRes) => {
            proxyRes.headers['cache-control'] = 'public, max-age=31536000, immutable';
            delete proxyRes.headers['expires'];
            delete proxyRes.headers['pragma'];
        },
    },
    {
        context: ['/yandex-img'],
        target: YANDEX_STORAGE,
        changeOrigin: true,
        secure: true,
        pathRewrite: { '^/yandex-img': '' },
        onProxyRes: (proxyRes) => {
            proxyRes.headers['cache-control'] = 'public, max-age=31536000, immutable';
            delete proxyRes.headers['expires'];
            delete proxyRes.headers['pragma'];
        },
    },
    {
        context: (pathname) => {
            if (pathname === '/') return false;
            // image proxy is handled by the entries above
            if (pathname.startsWith('/firebase-img')) return false;
            if (pathname.startsWith('/yandex-img')) return false;
            // webpack-dev-server internals / HMR
            if (pathname.startsWith('/ws')) return false;
            if (pathname.startsWith('/webpack')) return false;
            if (pathname.startsWith('/__')) return false;
            if (pathname.includes('hot-update')) return false;
            // static assets have an extension in the last path segment
            const lastSegment = pathname.substring(pathname.lastIndexOf('/') + 1);
            if (lastSegment.includes('.')) return false;
            return true;
        },
        target: BACKEND,
        changeOrigin: true,
        secure: true,
        ws: true,
    },
];
