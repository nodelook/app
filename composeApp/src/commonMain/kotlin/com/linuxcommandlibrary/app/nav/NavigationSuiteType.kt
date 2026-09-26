package com.nodelook.app.nav

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.window.core.layout.WindowSizeClass

// Material's default keeps a NavigationBar whenever height is compact (<480 dp), which on a
// landscape phone (e.g. Pixel 9a, ~873x411 dp) wastes the scarce vertical pixels. Promote to
// the rail purely on width so landscape phones, foldables, and tablets get the same side nav.
// Tabletop posture stays as a bottom bar — that surface is meant to be the input area.
internal fun navigationSuiteTypeFor(info: WindowAdaptiveInfo): NavigationSuiteType {
    val wsc = info.windowSizeClass
    return when {
        info.windowPosture.isTabletop -> NavigationSuiteType.NavigationBar

        wsc.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
            NavigationSuiteType.NavigationRail

        else -> NavigationSuiteType.NavigationBar
    }
}
