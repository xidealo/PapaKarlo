import SwiftUI
import shared
import FirebaseCore

struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
		Text(greet)
	}
}
