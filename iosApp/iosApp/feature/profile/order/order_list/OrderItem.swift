//
//  OrderItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import shared
import SwiftUI

struct OrderItem: Identifiable {
    let id: String
    let status: OrderStatus
    let code: String
    let dateTime: String
}
