package com.bunbeauty.shared.data.network.logger

import kotlinx.coroutines.CancellationException
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NetworkErrorLogPolicyTest {
    @Test
    fun shouldNotLogCancellationException() {
        assertFalse(
            NetworkErrorLogPolicy.shouldLog(
                httpCode = 0,
                throwable = CancellationException("cancelled"),
            ),
        )
    }

    @Test
    fun shouldNotLogExpectedClientHttpCodes() {
        assertFalse(
            NetworkErrorLogPolicy.shouldLog(
                httpCode = 404,
                throwable = IllegalStateException("not found"),
            ),
        )
    }

    @Test
    fun shouldNotLogTimeoutFailures() {
        assertFalse(
            NetworkErrorLogPolicy.shouldLog(
                httpCode = 0,
                throwable = TestTimeoutException(),
            ),
        )
    }

    @Test
    fun shouldLogUnexpectedServerErrors() {
        assertTrue(
            NetworkErrorLogPolicy.shouldLog(
                httpCode = 500,
                throwable = IllegalStateException("server error"),
            ),
        )
    }

    private class TestTimeoutException : Exception("request timeout")
}
