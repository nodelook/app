package com.nodelook.app.screenshots

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.nodelook.app.App
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.setResourceReaderAndroidContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication

class PhoneScreenshotTest {

    @get:Rule
    val paparazzi: Paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_9A.copy(softButtons = false),
        showSystemUi = true,
        maxPercentDifference = 0.1,
    )

    @OptIn(ExperimentalResourceApi::class)
    @Before
    fun setup() {
        setResourceReaderAndroidContext(paparazzi.context)
    }

    private fun snap(dark: Boolean, deeplink: String) {
        val theme = if (dark) "android:Theme.Material.NoActionBar" else "android:Theme.Material.Light.NoActionBar"
        paparazzi.unsafeUpdateConfig(theme = theme)
        paparazzi.snapshot {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                KoinApplication(application = { modules(screenshotKoinModules()) }) {
                    WithNavOwner {
                        App(initialDeeplink = deeplink, darkMode = dark)
                    }
                }
            }
        }
    }

    @Test fun screen01_openclaw() = snap(dark = true, deeplink = "/man/openclaw")

    @Test fun screen02_tips() = snap(dark = false, deeplink = "/tips")

    @Test fun screen03_systemInfo() = snap(dark = true, deeplink = "/basic/systeminformation")

    @Test fun screen04_search() = snap(dark = false, deeplink = "/search/mk")

    @Test fun screen05_basics() = snap(dark = true, deeplink = "/basics")

    @Test fun screen06_commands() = snap(dark = false, deeplink = "/")
}
