package com.nodelook.app.platform

import com.nodelook.shared.platform.ReviewHandler

class DesktopReviewHandler : ReviewHandler {
    override fun requestReviewIfNeeded() {
        // No-op on Desktop
    }

    override fun incrementAppStartCount() {
        // No-op on Desktop
    }
}
