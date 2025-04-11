//
//  iOSApp.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import shared
import SwiftUI

let iosComponent = IosComponent()
let dateUtil = DateUtil()

@main
struct PapaKarloSwiftApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    init() {
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            SplashView()
        }
    }
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
