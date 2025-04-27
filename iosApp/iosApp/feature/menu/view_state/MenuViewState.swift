//
//  MenuViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

class MenuViewState: NSObject, NSCopying {
    var menuItems: [MenuItem]
    var categoryItemModels: [CategoryItemModel]
    var isLoading: Bool
    var scrollToHorizontalPostion: String
    var discount: String?

    init(
        menuItems: [MenuItem],
        categoryItemModels: [CategoryItemModel],
        isLoading: Bool,
        scrollToHorizontalPostion: String,
        discount: String?
    ) {
        self.menuItems = menuItems
        self.categoryItemModels = categoryItemModels
        self.isLoading = isLoading
        self.scrollToHorizontalPostion = scrollToHorizontalPostion
        self.discount = discount
    }

    func copy(with _: NSZone? = nil) -> Any {
        let copy = MenuViewState(
            menuItems: menuItems,
            categoryItemModels: categoryItemModels,
            isLoading: isLoading,
            scrollToHorizontalPostion: scrollToHorizontalPostion,
            discount: discount
        )
        return copy
    }
}
