import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        KoinHelperKt.initIosKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}