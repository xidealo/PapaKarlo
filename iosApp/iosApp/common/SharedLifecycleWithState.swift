//
//  SharedLifecycleWithState.swift
//  iosApp
//
//  Created by Марк Шавловский on 16.12.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

protocol SharedLifecycleWithState {
    var listener: Closeable? { get }
    var eventsListener: Closeable? { get }
    func subscribe()
    func eventsSubscribe()
    func unsubscribe()
}
