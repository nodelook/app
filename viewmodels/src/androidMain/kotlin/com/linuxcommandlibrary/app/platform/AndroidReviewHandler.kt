package com.nodelook.app.platform

import com.nodelook.shared.platform.ReviewHandler

class AndroidReviewHandler : ReviewHandler {
    override fun requestReviewIfNeeded() {
        // No-op on Android - uses Play Store button in AppInfoDialog
    }

    override fun incrementAppStartCount() {
        // No-op on Android
    }
}
