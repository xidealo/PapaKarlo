//
//  MenuViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared

class MenuViewModel : ToolbarViewModel {
    @Published var menuViewState:MenuViewState = MenuViewState(
        menuItems: [],
        categoryItemModels: [],
        isLoading: true,
        scrollToPostion: "",
        scrollToHorizontalPostion: ""
    )
    
    override init(){
        super.init()
        
        iosComponent.provideMenuInteractor().getMenuSectionList { menuSectionList, error in
            (self.menuViewState.copy() as! MenuViewState).apply { copiedState in
                copiedState.isLoading = false
                copiedState.menuItems = self.getMenuItems(menuSectionList: menuSectionList ?? [])
                copiedState.categoryItemModels = self.getCategoryItems(menuSectionList: menuSectionList ?? [])
                DispatchQueue.main.async {
                    self.menuViewState = copiedState
                }
            }
        }
    }
    
    private func getMenuItems(menuSectionList:[MenuSection]) -> [MenuItem] {
        return menuSectionList.map { menuSection in
            MenuItem(categorySectionItem: CategorySectionItem(id: menuSection.category.uuid, name: menuSection.category.name, menuProdctItems: menuSection.menuProductList.map({ menuProduct in
                MenuProductItem(id: menuProduct.uuid, name: menuProduct.name, newPrice: String(menuProduct.newPrice) + Strings.CURRENCY, oldPrice: menuProduct.oldPrice as? Int, photoLink: menuProduct.photoLink)
            }))
            )
        }
    }
    
    private func getCategoryItems(menuSectionList:[MenuSection]) -> [CategoryItemModel] {
        return menuSectionList.map { menuSection in
            CategoryItemModel(key: "MenuCategoryHeaderItemModel "  + menuSection.category.uuid, id: menuSection.category.uuid, name: menuSection.category.name, isSelected: false)
        }
    }
    
    func addCartProductToCart(menuProductUuid:String){
        iosComponent.provideCartProductInteractor().addProductToCart(menuProductUuid: menuProductUuid) { cartProduct, error in
            if(cartProduct == nil){
                print("Not added")
            }
            else{
                print("Added")
            }
        }
    }
    
    func selectTagWithHorizontalScroll(tagName:String){
        (menuViewState.copy() as! MenuViewState).apply { newState in
            newState.categoryItemModels =  newState.categoryItemModels.map { categoryItem in
                if(tagName == categoryItem.name){
                    newState.scrollToHorizontalPostion = categoryItem.id
                    return CategoryItemModel(key: categoryItem.key, id: categoryItem.id, name: categoryItem.name, isSelected: true)
                }else{
                    return CategoryItemModel(key: categoryItem.key, id: categoryItem.id, name: categoryItem.name, isSelected: false)
                }
            }
            menuViewState = newState
        }
    }
    
    func seletTagWithScroll(tagName:String){
        (menuViewState.copy() as! MenuViewState).apply { newState in
            print(newState.categoryItemModels)
            newState.categoryItemModels =  newState.categoryItemModels.map { categoryItem in
                if(tagName == categoryItem.name){
                    newState.scrollToPostion = categoryItem.id
                    return CategoryItemModel(key: categoryItem.key, id: categoryItem.id, name: categoryItem.name, isSelected: true)
                }else{
                    return CategoryItemModel(key: categoryItem.key, id: categoryItem.id, name: categoryItem.name, isSelected: false)
                }
            }
            menuViewState = newState
        }
    }
}
