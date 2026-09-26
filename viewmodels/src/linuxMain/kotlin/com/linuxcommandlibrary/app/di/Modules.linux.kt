package com.nodelook.app.di

import com.nodelook.app.platform.LinuxAssetReader
import com.nodelook.app.platform.LinuxPreferencesStorage
import com.nodelook.app.platform.LinuxReviewHandler
import com.nodelook.app.platform.LinuxShareHandler
import com.nodelook.shared.platform.AssetReader
import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler
import com.nodelook.shared.platform.ShareHandler
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<AssetReader> { LinuxAssetReader() }
    single<PreferencesStorage> { LinuxPreferencesStorage() }
    single<ShareHandler> { LinuxShareHandler() }
    single<ReviewHandler> { LinuxReviewHandler() }
}
