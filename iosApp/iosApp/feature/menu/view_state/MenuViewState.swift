//
//  MenuUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

class MenuViewState : NSObject {
    let menuItems : [MenuItem]
    let categoryItemModels : [CategoryItemModel]
    let isLoading:Bool
    
    init(menuItems:[MenuItem], categoryItemModels: [CategoryItemModel], isLoading:Bool){
        self.menuItems = menuItems
        self.categoryItemModels = categoryItemModels
        self.isLoading = isLoading
    }
    
}
