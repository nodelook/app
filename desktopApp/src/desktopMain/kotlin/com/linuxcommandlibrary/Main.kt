package com.nodelook

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.nodelook.app.App
import com.nodelook.app.di.commonModule
import com.nodelook.app.di.platformModule
import org.koin.core.context.startKoin

fun main() {
    // Tell macOS AppKit to follow the system dark/light setting for the window title bar
    // and other native chrome. Without this, the JVM defaults to "aqua" (light) regardless
    // of the system theme. Harmless no-op on Linux/Windows.
    System.setProperty("apple.awt.application.appearance", "system")

    application {
        startKoin {
            modules(commonModule, platformModule())
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "NodeLook",
            state = rememberWindowState(width = 900.dp, height = 700.dp),
        ) {
            App()
        }
    }
}
