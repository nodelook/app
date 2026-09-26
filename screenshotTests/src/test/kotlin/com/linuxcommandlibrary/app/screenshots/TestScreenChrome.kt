package com.nodelook.app.screenshots

import com.nodelook.app.di.commonModule
import com.nodelook.shared.platform.AssetReader
import com.nodelook.shared.platform.PreferencesStorage
import com.nodelook.shared.platform.ReviewHandler
import com.nodelook.shared.platform.ShareHandler
import org.koin.core.module.Module
import org.koin.dsl.module

fun screenshotKoinModules(): List<Module> = listOf(
    module {
        single<AssetReader> { JvmAssetReader(resolveAssetsRoot()) }
        single<PreferencesStorage> { FakePreferencesStorage() }
        single<ShareHandler> { NoopShareHandler }
        single<ReviewHandler> { NoopReviewHandler }
    },
    commonModule,
)
