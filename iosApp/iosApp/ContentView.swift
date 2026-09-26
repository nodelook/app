import SwiftUI

/// Native 3-tab SwiftUI shell. Tab selection is driven by AppRouter so deep links
/// can switch tabs programmatically.
struct ContentView: View {
    @EnvironmentObject private var router: AppRouter

    var body: some View {
        TabView(selection: $router.selectedTab) {
            BasicCategoriesView()
                .tag(AppRouter.Tab.basics)
                .tabItem {
                    Label("Basics", systemImage: "book.fill")
                }

            TipsView()
                .tag(AppRouter.Tab.tips)
                .tabItem {
                    Label("Tips", systemImage: "lightbulb.fill")
                }

            CommandsTabView()
                .tag(AppRouter.Tab.commands)
                .tabItem {
                    Label("Commands", systemImage: "terminal")
                }
        }
        .tint(.brandRed)
    }
}
