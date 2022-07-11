//
//  PapaKarloSwiftApp.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import SwiftUI
import shared

let iosComponent = IosComponent()

@main
struct PapaKarloSwiftApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    init(){
        KoinKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            SplashView()
        }
    }
    
}
