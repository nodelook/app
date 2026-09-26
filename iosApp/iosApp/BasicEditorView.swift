import ComposeApp
import SwiftUI

/// Card-layout Basics screen used for editor-style categories (vim/emacs/etc.) and
/// shell scripting / tmux / regular expressions / terminal games. Each BasicGroup
/// is a rounded card with optional title and rendered markdown sections.
struct BasicEditorView: View {
    let categoryId: String
    let title: String
    let onManTap: (String) -> Void
    @StateObject private var store: BasicEditorStore

    init(categoryId: String, title: String, onManTap: @escaping (String) -> Void = { _ in }) {
        self.categoryId = categoryId
        self.title = title
        self.onManTap = onManTap
        _store = StateObject(wrappedValue: BasicEditorStore(categoryId: categoryId))
    }

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(store.groups, id: \.id) { group in
                    EditorCard(
                        group: group,
                        showTitle: store.showTitles,
                        onTapMan: { name in
                            onManTap(name)
                            Haptics.selection()
                        },
                        onTapLink: store.tapLink,
                        onTapUrl: store.tapUrl
                    )
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
        }
        .background(Color(.systemGroupedBackground))
        .navigationTitle(title)
        .navigationBarTitleDisplayMode(.inline)
    }
}

private struct EditorCard: View {
    let group: BasicGroup_
    let showTitle: Bool
    let onTapMan: (String) -> Void
    let onTapLink: (String) -> Void
    let onTapUrl: (String) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if showTitle {
                Text(group.description_)
                    .font(.headline)
            }
            MarkdownView(
                elements: group.sections,
                onTapMan: onTapMan,
                onTapLink: onTapLink,
                onTapUrl: onTapUrl
            )
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color(.secondarySystemGroupedBackground))
        .cornerRadius(10)
    }
}

@MainActor
private final class BasicEditorStore: ObservableObject {
    private let viewModel: BasicEditorViewModel
    @Published private(set) var groups: [BasicGroup_] = []
    let showTitles: Bool

    private var task: Task<Void, Never>?

    init(categoryId: String) {
        viewModel = KoinHelperKt.makeBasicEditorViewModel(categoryId: categoryId)
        showTitles = viewModel.showTitles
        task = Task { [weak self] in
            guard let self else { return }
            for await items in self.viewModel.groups {
                self.groups = items
            }
        }
    }

    deinit {
        task?.cancel()
        viewModel.cancel()
    }

    func tapLink(_: String) {
        // No external app launch on iOS for "settings"/"terminal" — silently ignore
    }

    func tapUrl(_ url: String) {
        if let target = URL(string: url) {
            UIApplication.shared.open(target)
        }
    }
}
