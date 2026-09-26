package com.nodelook.app.platform

import com.nodelook.shared.platform.ShareHandler
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class DesktopShareHandler : ShareHandler {
    override fun shareText(text: String) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(StringSelection(text), null)
    }
}
