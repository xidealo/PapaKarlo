//
//  OrderItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct OrderItem :Identifiable {
    let id:UUID
    let status:String
    let code:String
    let dateTime:String
}
