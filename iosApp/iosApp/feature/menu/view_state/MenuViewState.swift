//
//  MenuUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

class MenuViewState : NSObject, NSCopying {
    var menuItems : [MenuItem]
    var isLoading : Bool
    var scrollToHorizontalPostion : String
    var discount : String?

    init(
        menuItems:[MenuItem],
        isLoading:Bool,
        scrollToHorizontalPostion:String,
        discount:String?
    ){
        self.menuItems = menuItems
        self.isLoading = isLoading
        self.scrollToHorizontalPostion = scrollToHorizontalPostion
        self.discount = discount
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = MenuViewState(
            menuItems: menuItems,
            isLoading: isLoading,
            scrollToHorizontalPostion:scrollToHorizontalPostion,
            discount:discount
        )
        return copy
    }
}


class MenuCategoryViewState : NSObject {
    var categoryItems : [CategoryItemModel]
    var scrollToHorizontalPostion : String

    init(
        categoryItems : [CategoryItemModel],
        scrollToHorizontalPostion:String
    ){
        self.categoryItems = categoryItems
        self.scrollToHorizontalPostion = scrollToHorizontalPostion
    }
}


