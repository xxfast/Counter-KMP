import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
      NavigationStack {
        List {
          TimerScreen()
          TallyScreen()
        }
        .navigationTitle("Counter-KMP")
      }
		}
	}
}
