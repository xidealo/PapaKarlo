//
//  MenuViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared

class MenuViewModel : ObservableObject {
    
    @Published var menuViewState: MenuViewState = MenuViewState(
        menuItems: [],
        isLoading: true,
        scrollToHorizontalPostion: "",
        discount: nil
    )
    
    @Published var menuCategoryViewState: MenuCategoryViewState = MenuCategoryViewState(
        categoryItems: [],
        scrollToHorizontalPostion: ""
    )
    
    @Published var scrollToPostion = "no pos"
    
    var lastDisappearIndex = 1
    var lastAppearIndex = 2
    var canCalculate = true
    let addMenuProductUseCase = iosComponent.provideAddMenuProductUseCase()
    
    init(){
        iosComponent.provideMenuInteractor().getMenuSectionList { menuSectionList, error in
            if(error != nil){
                print("error")
                return
            }
            
            DispatchQueue.main.async {
                self.menuCategoryViewState = MenuCategoryViewState(
                    categoryItems: self.getCategoryItems(menuSectionList: menuSectionList ?? []),
                    scrollToHorizontalPostion: ""
                )
                
                iosComponent.provideGetDiscountUseCase().invoke { discount, err in
                    (self.menuViewState.copy() as! MenuViewState).apply { copiedState in
                        copiedState.isLoading = false
                        copiedState.menuItems = self.getMenuItems(menuSectionList: menuSectionList ?? [])
                        if let notNullDiscount = discount?.firstOrderDiscount {
                            copiedState.discount = "\(notNullDiscount)"
                        }
                        DispatchQueue.main.async {
                            self.menuViewState = copiedState
                        }
                    }
                }
            }
        }
    }
    
    private func getMenuItems(menuSectionList:[MenuSection]) -> [MenuItem] {
        return menuSectionList.map { menuSection in
            MenuItem(categorySectionItem: CategorySectionItem(id: menuSection.category.uuid, name: menuSection.category.name, menuProdctItems: menuSection.menuProductList.map({ menuProduct in
                MenuProductItem(
                    id: menuProduct.uuid + menuSection.category.uuid,
                    productUuid: menuProduct.uuid,
                    name: menuProduct.name,
                    newPrice: String(menuProduct.newPrice) + Strings.CURRENCY,
                    oldPrice: menuProduct.oldPrice as? Int,
                    photoLink: menuProduct.photoLink,
                    hasAdditions: !menuProduct.additionGroups.isEmpty
                )
            }))
            )
        }
    }
    
    private func getCategoryItems(menuSectionList:[MenuSection]) -> [CategoryItemModel] {
        return menuSectionList.map { menuSection in
            CategoryItemModel(
                key: "MenuCategoryHeaderItemModel "  + menuSection.category.uuid,
                id: menuSection.category.uuid,
                name: menuSection.category.name,
                isSelected: false
            )
        }
    }
    
    func addCartProductToCart(menuProductItem:MenuProductItem){
        addMenuProductUseCase.invoke(menuProductUuid: menuProductItem.productUuid) { err in
            print("Not added")
        }
    }
    
    func selectTagWithHorizontalScroll(selectIndex:Int) {
        var scrollToHorizontalPostion = ""
        
        let categoryListWithSelectedCategory =  menuCategoryViewState.categoryItems.enumerated().map { (index, categoryItem)  in
            if(selectIndex == index){
                scrollToHorizontalPostion = categoryItem.id
                return CategoryItemModel(
                    key: categoryItem.key,
                    id: categoryItem.id,
                    name: categoryItem.name,
                    isSelected: true
                )
            }else{
                return CategoryItemModel(
                    key: categoryItem.key,
                    id: categoryItem.id,
                    name: categoryItem.name,
                    isSelected: false
                )
            }
        }
        
        self.menuCategoryViewState = MenuCategoryViewState(
            categoryItems: categoryListWithSelectedCategory,
            scrollToHorizontalPostion: scrollToHorizontalPostion
        )
    }
    
    func seletTagWithScroll(tagName:String){
        canCalculate = false
        var scrollToHorizontalPostion = ""
        
        let categoryListWithSelectedCategory = menuCategoryViewState.categoryItems.map { categoryItem in
            if(tagName == categoryItem.name){
                
                let isFirstItem = menuCategoryViewState.categoryItems.firstIndex(where: { categoryItem in
                    categoryItem.name == tagName
                }) == 0
                
                if (menuViewState.discount != nil && isFirstItem) {
                    scrollToPostion = DISCOUNT_ID
                    print("scroll to discount")
                }else{
                    scrollToPostion = categoryItem.id
                    print("scroll categoryItem.id \(categoryItem.id)")
                }
                
                scrollToHorizontalPostion = categoryItem.id
                
                return CategoryItemModel(
                    key: categoryItem.key,
                    id: categoryItem.id,
                    name: categoryItem.name,
                    isSelected: true
                )
            }else{
                return CategoryItemModel(
                    key: categoryItem.key,
                    id: categoryItem.id,
                    name: categoryItem.name,
                    isSelected: false
                )
            }
        }
        
        self.menuCategoryViewState = MenuCategoryViewState(
            categoryItems: categoryListWithSelectedCategory,
            scrollToHorizontalPostion: scrollToHorizontalPostion
        )
        
    }
    
    func checkAppear(index:Int){
        
        let isItemWichToScrolled = scrollToPostion == menuCategoryViewState.categoryItems[index].id
        let isItDiscountItem = scrollToPostion == DISCOUNT_ID
        
        if(isItemWichToScrolled || isItDiscountItem){
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(500), execute: {
                self.canCalculate = true
            })
        }
        
        if !canCalculate {
            return
        }
        
        lastAppearIndex = index
        
        if(lastDisappearIndex > lastAppearIndex){
            selectTagWithHorizontalScroll(selectIndex: index)
        }
    }
    
    func checkDisappear(index:Int){
        print("dissapear index \(index)")
        if !canCalculate {
            return
        }
        
        lastDisappearIndex = index
        
        if(lastAppearIndex > lastDisappearIndex){
            selectTagWithHorizontalScroll(selectIndex: index + 1 )
        }
    }
}
