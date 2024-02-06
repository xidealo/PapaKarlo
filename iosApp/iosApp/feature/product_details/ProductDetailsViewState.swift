//
//  MenuProductUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import Foundation
import shared

struct ProductDetailsViewState {
    let photoLink: String
    let name: String
    let size: String
    let oldPrice: String?
    let newPrice: String
    let priceWithAdditions: String
    let description: String
    let additionList: [AdditionItem]
    let screenState : ProductDetailsStateDataState.ScreenState
}

class AdditionItem: Identifiable {
    let id:String
    
    init(id:String){
        self.id = id
    }

    class AdditionHeaderItem : AdditionItem {
        let name:String
        
        init(id: String, name: String) {
            self.name = name
            super.init(id: id)
        }
    }
    
    class AdditionListItem : AdditionItem {
        let product:MenuProductAdditionItem
        let isMultiple: Bool
        
        init(id: String, product: MenuProductAdditionItem, isMultiple: Bool) {
            self.product = product
            self.isMultiple = isMultiple
            super.init(id: id)
        }
    }
}

struct MenuProductAdditionItem {
    let uuid: String
    let isSelected: Bool
    let name: String
    let price: String?
    let isLast: Bool
    let photoLink: String
    let groupId: String
}

