import ComposeApp
import SwiftUI

@main
struct iOSApp: App {
    @StateObject private var router = AppRouter()

    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(router)
                .onOpenURL { url in
                    router.handle(url: url)
                }
        }
    }
}
