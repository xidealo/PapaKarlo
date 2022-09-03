//
//  MenuUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

class MenuViewState : NSObject, NSCopying {
    var menuItems : [MenuItem]
    var categoryItemModels : [CategoryItemModel]
    var isLoading:Bool
    
    init(menuItems:[MenuItem], categoryItemModels: [CategoryItemModel], isLoading:Bool){
        self.menuItems = menuItems
        self.categoryItemModels = categoryItemModels
        self.isLoading = isLoading
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = MenuViewState(menuItems: menuItems, categoryItemModels: categoryItemModels, isLoading: isLoading)
        return copy
    }
}
