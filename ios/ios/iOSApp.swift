import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
      NavigationStack {
        List {
          TimerScreen()
          TallyScreen()
          FactsScreen()
        }
        .navigationTitle("Counter-KMP")
      }
		}
	}
}
