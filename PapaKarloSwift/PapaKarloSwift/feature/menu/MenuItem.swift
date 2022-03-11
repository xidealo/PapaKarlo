//
//  MenuItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI
enum MenuItem: Identifiable {
    var id : String { UUID().uuidString }

    case categorySectionItem(CategorySectionItem)
    case menuProductItem(MenuProductItem)
}
