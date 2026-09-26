package com.nodelook.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.nodelook.app.ui.AppIcons

actual val showRateAppButton: Boolean = false
actual val showFastScrollBar: Boolean = false
actual val backIcon: ImageVector = AppIcons.ArrowBack
actual val shareButtonIcon: ImageVector = AppIcons.ContentCopy
actual val shareButtonDescription: String = "Copy to clipboard"

@Composable
actual fun rememberOpenAppAction(): (String) -> Unit = {}
