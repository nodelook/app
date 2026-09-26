package com.nodelook.app.screenshots

import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler

class FakePreferencesStorage : PreferencesStorage {
    private val data = mutableMapOf<String, Any>(
        // Force expanded command-detail sections so screenshots show content rather than
        // a column of section headers (Android default is collapsed).
        "auto_expand_sections" to true,
    )
    override fun getString(key: String, defaultValue: String): String = (data[key] as? String) ?: defaultValue
    override fun putString(key: String, value: String) {
        data[key] = value
    }
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = (data[key] as? Boolean) ?: defaultValue
    override fun putBoolean(key: String, value: Boolean) {
        data[key] = value
    }
    override fun getInt(key: String, defaultValue: Int): Int = (data[key] as? Int) ?: defaultValue
    override fun putInt(key: String, value: Int) {
        data[key] = value
    }
}

object NoopReviewHandler : ReviewHandler {
    override fun requestReviewIfNeeded() {}
    override fun incrementAppStartCount() {}
}
