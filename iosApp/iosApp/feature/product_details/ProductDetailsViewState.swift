//
//  ProductDetailsViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import Foundation
import shared

struct ProductDetailsViewState {
    var photoLink: String
    var name: String
    var size: String
    var oldPrice: String?
    var newPrice: String
    var priceWithAdditions: String
    var description: String
    var additionList: [AdditionItem]
    var screenState: ProductDetailsStateDataState.ScreenState
}

class AdditionItem: Identifiable {
    let id: String

    init(id: String) {
        self.id = id
    }

    class AdditionHeaderItem: AdditionItem {
        let name: String

        init(id: String, name: String) {
            self.name = name
            super.init(id: id)
        }
    }

    class AdditionListItem: AdditionItem {
        let product: MenuProductAdditionItem
        let isMultiple: Bool

        init(id: String, product: MenuProductAdditionItem, isMultiple: Bool) {
            self.product = product
            self.isMultiple = isMultiple
            super.init(id: id)
        }
    }
}

struct MenuProductAdditionItem {
    var uuid: String
    var isSelected: Bool
    var name: String
    var price: String?
    var isLast: Bool
    var photoLink: String
    var groupId: String
}
