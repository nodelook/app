package com.nodelook.app.screenshots

import com.nodelook.shared.platform.ShareHandler

object NoopShareHandler : ShareHandler {
    override fun shareText(text: String) {}
}
