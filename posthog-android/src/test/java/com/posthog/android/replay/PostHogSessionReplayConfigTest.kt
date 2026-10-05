package com.posthog.android.replay

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class PostHogSessionReplayConfigTest {
    @Test
    fun `surface capture and periodic capture are off by default`() {
        val config = PostHogSessionReplayConfig()

        assertFalse(config.captureSurfaceViews)
        assertNull(config.captureIntervalMs)
    }

    @Test
    fun `the constructor accepts surface capture and a positive capture interval`() {
        val config = PostHogSessionReplayConfig(captureSurfaceViews = true, captureIntervalMs = 2000)

        assertTrue(config.captureSurfaceViews)
        assertEquals(2000, config.captureIntervalMs)
    }

    @Test
    fun `the constructor rejects a zero capture interval`() {
        assertFailsWith<IllegalArgumentException> {
            PostHogSessionReplayConfig(captureIntervalMs = 0)
        }
    }

    @Test
    fun `the constructor rejects a negative capture interval`() {
        assertFailsWith<IllegalArgumentException> {
            PostHogSessionReplayConfig(captureIntervalMs = -1)
        }
    }

    @Test
    fun `the capture interval can be set and cleared`() {
        val config = PostHogSessionReplayConfig()

        config.captureIntervalMs = 1
        assertEquals(1, config.captureIntervalMs)

        config.captureIntervalMs = null
        assertNull(config.captureIntervalMs)
    }

    @Test
    fun `setting a non-positive capture interval is rejected and keeps the previous value`() {
        val config = PostHogSessionReplayConfig(captureIntervalMs = 500)

        assertFailsWith<IllegalArgumentException> {
            config.captureIntervalMs = 0
        }
        assertEquals(500, config.captureIntervalMs)
    }

    @Test
    fun `surface capture can be toggled`() {
        val config = PostHogSessionReplayConfig()

        config.captureSurfaceViews = true
        assertTrue(config.captureSurfaceViews)

        config.captureSurfaceViews = false
        assertFalse(config.captureSurfaceViews)
    }
}
