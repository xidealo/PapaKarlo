//
//  SharedLifecycle.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.12.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

protocol SharedLifecycle {
    var eventsListener: Closeable? { get }
    func eventsSubscribe()
    func unsubscribe()
}
