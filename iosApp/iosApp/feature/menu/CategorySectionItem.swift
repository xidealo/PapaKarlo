//
//  CategorySectionItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI

struct CategorySectionItem: Identifiable {
    let id: String
    let name: String
    let menuProdctItems: [MenuProductItem]
}
