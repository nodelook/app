package com.nodelook.app.di

import com.nodelook.app.platform.AndroidAssetReader
import com.nodelook.app.platform.AndroidPreferencesStorage
import com.nodelook.app.platform.AndroidReviewHandler
import com.nodelook.app.platform.AndroidShareHandler
import com.nodelook.shared.platform.AssetReader
import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler
import com.nodelook.shared.platform.ShareHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<AssetReader> { AndroidAssetReader(androidContext()) }
    single<PreferencesStorage> { AndroidPreferencesStorage(androidContext()) }
    single<ShareHandler> { AndroidShareHandler(androidContext()) }
    single<ReviewHandler> { AndroidReviewHandler() }
}
