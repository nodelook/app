package com.nodelook.app.ui.screens.basicgroups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nodelook.app.NavEvent
import com.nodelook.app.data.BasicsRepository
import com.nodelook.app.ui.composables.PaneTopBar
import com.nodelook.app.ui.screens.basiccategories.BasicCategoriesViewModel
import org.koin.compose.currentKoinScope
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun BasicGroupDetailPane(
    categoryId: String,
    expandGroupId: Long?,
    onBack: () -> Unit,
    onNavigate: (NavEvent) -> Unit,
) {
    val categoriesViewModel: BasicCategoriesViewModel = koinInject()
    val basicsRepository: BasicsRepository = koinInject()
    val categories by categoriesViewModel.basicCategories.collectAsState()
    val title = categories.firstOrNull { it.id == categoryId }?.title.orEmpty()
    val usesCardLayout = basicsRepository.usesCardLayout(categoryId)
    val koinScope = currentKoinScope()
    var focusedGroupId by remember(categoryId) { mutableStateOf(expandGroupId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        PaneTopBar(title = title, onBack = onBack)
        if (usesCardLayout) {
            val editorViewModel = remember(categoryId, koinScope) {
                koinScope.get<BasicEditorViewModel> { parametersOf(categoryId) }
            }
            BasicEditorScreen(viewModel = editorViewModel, onNavigate = onNavigate)
        } else {
            val groupsViewModel = remember(categoryId, koinScope) {
                koinScope.get<BasicGroupsViewModel> { parametersOf(categoryId) }
            }
            BasicGroupsScreen(
                viewModel = groupsViewModel,
                onNavigate = onNavigate,
                focusGroupId = focusedGroupId,
                onFocusConsumed = { focusedGroupId = null },
            )
        }
    }
}
