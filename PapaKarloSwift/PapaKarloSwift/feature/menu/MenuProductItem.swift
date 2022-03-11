//
//  MenuProductItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI
struct MenuProductItem:Identifiable  {
     let id: UUID
     let name: String
     let newPrice: String
     let oldPrice: String?
     let photoLink: String
}
