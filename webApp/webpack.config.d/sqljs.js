// Copies the sql.js WebAssembly binary next to the bundle so the SQLDelight
// sqljs-driver can load it at runtime.
const CopyWebpackPlugin = require('copy-webpack-plugin');

config.resolve = config.resolve || {};
// okio (used by Coil for its default disk-cache FileSystem) tries to read the
// node "os" / "path" modules (e.g. os.tmpdir()) at construction time. In the
// browser these don't exist, so we provide browser polyfills. "fs"/"crypto" stay
// disabled because the web ImageLoader is configured without a disk cache, so the
// filesystem is never actually used.
config.resolve.fallback = Object.assign({}, config.resolve.fallback, {
    fs: false,
    crypto: false,
    os: require.resolve('os-browserify/browser'),
    path: require.resolve('path-browserify'),
});

config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: '../../node_modules/sql.js/dist/sql-wasm.wasm',
                to: '.',
            },
        ],
    })
);
