package com.nodelook.app.di

import com.nodelook.app.platform.IosAssetReader
import com.nodelook.app.platform.IosPreferencesStorage
import com.nodelook.app.platform.IosReviewHandler
import com.nodelook.app.platform.IosShareHandler
import com.nodelook.shared.platform.AssetReader
import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler
import com.nodelook.shared.platform.ShareHandler
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<AssetReader> { IosAssetReader() }
    single<PreferencesStorage> { IosPreferencesStorage() }
    single<ShareHandler> { IosShareHandler() }
    single<ReviewHandler> { IosReviewHandler(get()) }
}
