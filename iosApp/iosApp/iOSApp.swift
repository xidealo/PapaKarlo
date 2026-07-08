//
//  iOSApp.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import FirebaseAnalytics
import FirebaseCore
import shared
import SwiftUI

let iosComponent = IosComponent()

@main
struct PapaKarloSwiftApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    init() {
        // Must configure Firebase before any Analytics/Messaging/Koin Firebase calls.
        // Analytics was previously started eagerly from Koin (createdAtStart) and crashed
        // Release builds with com.firebase.installations.
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
        #if DEBUG
        Analytics.setAnalyticsCollectionEnabled(false)
        #else
        Analytics.setAnalyticsCollectionEnabled(true)
        #endif
        KoinKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            VStack{
                ComposeView()
                    .ignoresSafeArea()
            }
        }
    }
    
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let vc = AppIosKt.MainViewController(
            flavor: Bundle.main.object(forInfoDictionaryKey: "CFBundleName") as? String ?? ""
        )
        
        vc.view.insetsLayoutMarginsFromSafeArea = false
        vc.additionalSafeAreaInsets = .zero
        
        return vc
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

protocol HasApply {}

extension HasApply {
    func applyAndReturn(closure: (Self) -> Void) -> Self {
        closure(self)
        return self
    }

    func apply(closure: (Self) -> Void) {
        closure(self)
    }
}

extension NSObject: HasApply {}
