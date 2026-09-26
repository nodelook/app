package com.nodelook.app.ios

import com.nodelook.app.data.BasicsRepository
import com.nodelook.app.di.commonModule
import com.nodelook.app.di.platformModule
import com.nodelook.app.ui.screens.basiccategories.BasicCategoriesViewModel
import com.nodelook.app.ui.screens.basicgroups.BasicEditorViewModel
import com.nodelook.app.ui.screens.basicgroups.BasicGroupsViewModel
import com.nodelook.app.ui.screens.commanddetail.CommandDetailViewModel
import com.nodelook.app.ui.screens.commandlist.CommandListViewModel
import com.nodelook.app.ui.screens.search.SearchViewModel
import com.nodelook.app.ui.screens.tips.TipsViewModel
import com.nodelook.shared.platform.ShareHandler
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

/**
 * Swift-facing factory functions for Koin-managed ViewModels.
 *
 * Swift cannot ergonomically call Koin's `parametersOf(...)` API; these top-level
 * functions wrap that complexity. Each function returns a freshly constructed VM.
 *
 * Lifecycle: SwiftUI views own a single VM instance and call its `cancel()` method
 * on disappear (or via `deinit`) to release in-flight coroutines.
 */

fun makeSearchViewModel(): SearchViewModel = getKoin().get()

fun makeCommandListViewModel(): CommandListViewModel = getKoin().get()

fun makeCommandDetailViewModel(commandName: String): CommandDetailViewModel = getKoin().get { parametersOf(commandName) }

fun makeBasicCategoriesViewModel(): BasicCategoriesViewModel = getKoin().get()

fun makeBasicGroupsViewModel(categoryId: String): BasicGroupsViewModel = getKoin().get { parametersOf(categoryId) }

fun makeBasicEditorViewModel(categoryId: String): BasicEditorViewModel = getKoin().get { parametersOf(categoryId) }

fun makeTipsViewModel(): TipsViewModel = getKoin().get()

/**
 * Categories like vim/emacs/tmux/regularexpressions/terminalgames render as a card
 * grid (BasicEditorView) instead of the collapsible groups list (BasicGroupsView).
 */
fun categoryUsesCardLayout(categoryId: String): Boolean = getKoin().get<BasicsRepository>().usesCardLayout(categoryId)

/**
 * Returns the singleton iOS share handler (presents UIActivityViewController).
 */
fun makeShareHandler(): ShareHandler = getKoin().get()

/**
 * Bootstraps Koin's dependency graph. Call once from the iOS app's entry point.
 */
fun doInitKoin() {
    startKoin {
        modules(commonModule, platformModule())
    }
}
