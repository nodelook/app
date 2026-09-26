import SwiftUI

/// Drives cross-tab navigation triggered from outside the SwiftUI hierarchy
/// (incoming deep links, push notifications, etc.).
///
/// `nodelook://` scheme:
/// - `nodelook://man/<cmd>` → switch to Commands tab, select that command
/// - `nodelook://basic/<category>` → switch to Basics tab, select that category
/// - `nodelook://basics` → Basics tab
/// - `nodelook://tips` → Tips tab
/// - `nodelook://commands` → Commands tab
@MainActor
final class AppRouter: ObservableObject {
    enum Tab: Hashable {
        case basics, tips, commands
    }

    @Published var selectedTab: Tab = .basics

    /// When set, CommandsTabView selects this command in its split view.
    /// Cleared after consumption.
    @Published var commandDetailTarget: String?

    /// When set, BasicCategoriesView selects this category in its split view.
    /// Cleared after consumption.
    @Published var basicsCategoryTarget: String?

    /// Mirrors the rules in the shared Kotlin `parseDeeplink(url:)`.
    func handle(url: URL) {
        let path = (url.host.map { "/\($0)" } ?? "") + url.path
        let trimmed = path.hasSuffix("/") ? String(path.dropLast()) : path

        if trimmed.contains("/man/") {
            let cmd = trimmed
                .components(separatedBy: "/man/").last?
                .replacingOccurrences(of: ".html", with: "") ?? ""
            if !cmd.isEmpty {
                selectedTab = .commands
                commandDetailTarget = cmd
            }
        } else if trimmed.contains("/basic/") {
            let cat = trimmed
                .components(separatedBy: "/basic/").last?
                .replacingOccurrences(of: ".html", with: "") ?? ""
            if !cat.isEmpty {
                selectedTab = .basics
                basicsCategoryTarget = cat
            }
        } else if trimmed.hasSuffix("/basics") || trimmed.hasSuffix("/basics.html") {
            selectedTab = .basics
        } else if trimmed.hasSuffix("/tips") || trimmed.hasSuffix("/tips.html") {
            selectedTab = .tips
        } else if trimmed.isEmpty || trimmed == "/index.html" || trimmed.hasSuffix("/commands") {
            selectedTab = .commands
        }
    }
}
