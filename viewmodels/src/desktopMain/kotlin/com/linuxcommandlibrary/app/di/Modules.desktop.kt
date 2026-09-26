package com.nodelook.app.di

import com.nodelook.app.platform.DesktopAssetReader
import com.nodelook.app.platform.DesktopPreferencesStorage
import com.nodelook.app.platform.DesktopReviewHandler
import com.nodelook.app.platform.DesktopShareHandler
import com.nodelook.shared.platform.AssetReader
import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler
import com.nodelook.shared.platform.ShareHandler
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<AssetReader> { DesktopAssetReader() }
    single<PreferencesStorage> { DesktopPreferencesStorage() }
    single<ShareHandler> { DesktopShareHandler() }
    single<ReviewHandler> { DesktopReviewHandler() }
}
