package com.nodelook.app.platform

/**
 * Platform-dependent flags consumed by the shared ViewModel layer.
 * Compose-specific platform expects (backIcon, rememberOpenAppAction, etc.) live
 * in composeApp/.../platform/Platform.kt — those are only relevant for the Compose UI
 * (Android / Desktop) and are deliberately not in this module.
 */
expect val showAndroidTerminalTip: Boolean

/**
 * Default value for auto-expanding all sections on the command detail screen
 * when the user hasn't set a preference yet. iOS users typically expect content
 * to be visible by default; Android/Desktop start collapsed for a tighter view.
 */
expect val defaultAutoExpandCommandSections: Boolean
