//
//  MenuViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared

class MenuViewModel : ObservableObject {
    @Published var menuViewState:MenuViewState = MenuViewState(menuItems: [], categoryItemModels: [], isLoading: false)

    
    init(){
        menuViewState =  MenuViewState(menuItems: [], categoryItemModels: [], isLoading: true)

        iosComponent.provideMenuInteractor().getMenuSectionList { menuSectionList, error in
            self.menuViewState = MenuViewState(menuItems: self.getMenuItems(menuSectionList: menuSectionList ?? []), categoryItemModels: self.getCategoryItems(menuSectionList: menuSectionList ?? []), isLoading: false)
                                              
        }
    }
    
    
    func getMenuItems(menuSectionList:[MenuSection]) -> [MenuItem] {
        return menuSectionList.map { menuSection in
            MenuItem(categorySectionItem: CategorySectionItem(id: menuSection.category.uuid, name: menuSection.category.name, menuProdctItems: menuSection.menuProductList.map({ menuProduct in
                MenuProductItem(id: menuProduct.uuid, name: menuProduct.name, newPrice: String(menuProduct.newPrice), oldPrice: menuProduct.oldPrice as? Int, photoLink: menuProduct.photoLink)
            }))
            )
        }
    }
    
    func getCategoryItems(menuSectionList:[MenuSection]) -> [CategoryItemModel] {
        return menuSectionList.map { menuSection in
            CategoryItemModel(key: "MenuCategoryHeaderItemModel "  + menuSection.category.uuid, id: menuSection.category.uuid, name: menuSection.category.name, isSelected: false)
        }
    }
}
