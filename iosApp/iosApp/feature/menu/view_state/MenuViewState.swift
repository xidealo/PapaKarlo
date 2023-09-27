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
    var isLoading : Bool
    var scrollToPostion : String
    var scrollToHorizontalPostion : String
    var discount : String?

    init(
        menuItems:[MenuItem],
        categoryItemModels: [CategoryItemModel],
        isLoading:Bool,
        scrollToPostion:String,
        scrollToHorizontalPostion:String,
        discount:String?
    ){
        self.menuItems = menuItems
        self.categoryItemModels = categoryItemModels
        self.isLoading = isLoading
        self.scrollToPostion = scrollToPostion
        self.scrollToHorizontalPostion = scrollToHorizontalPostion
        self.discount = discount
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = MenuViewState(
            menuItems: menuItems,
            categoryItemModels: categoryItemModels,
            isLoading: isLoading,
            scrollToPostion:scrollToPostion,
            scrollToHorizontalPostion:scrollToHorizontalPostion,
            discount:discount
        )
        return copy
    }
}
