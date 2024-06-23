//
//  PaymentMethodUi.swift
//  iosApp
//
//  Created by Марк Шавловский on 22.06.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct PaymentMethodUI {
    var uuid: String
    var name: LocalizedStringKey
    var value: PaymentMethodValueUI?
}

struct PaymentMethodValueUI {
    var value: String
    var valueToCopy: String
}
