//
//  MenuItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI

struct MenuItem: Identifiable {
    var id : String { UUID().uuidString }
    let categorySectionItem:CategorySectionItem
}
