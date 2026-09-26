import UIKit

/// Tiny haptic helper. Use selection() for taps and impact() for transitions.
enum Haptics {
    private static let selectionGenerator = UISelectionFeedbackGenerator()
    private static let impactGenerator = UIImpactFeedbackGenerator(style: .light)

    static func selection() {
        selectionGenerator.selectionChanged()
    }

    static func impact() {
        impactGenerator.impactOccurred()
    }
}
