package com.nodelook.shared.platform

interface ReviewHandler {
    fun requestReviewIfNeeded()
    fun incrementAppStartCount()
}
