import SwiftUI

/// Shown in the detail column of a NavigationSplitView when nothing is selected
/// (mostly visible on iPad landscape — iPhone collapses to single-column and
/// users never see this).
struct DetailPlaceholder: View {
    let systemImage: String
    let message: String

    var body: some View {
        VStack(spacing: 12) {
            Image(systemName: systemImage)
                .font(.system(size: 60, weight: .light))
                .foregroundColor(.secondary.opacity(0.5))
            Text(message)
                .font(.title3)
                .foregroundColor(.secondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(.systemGroupedBackground))
    }
}
