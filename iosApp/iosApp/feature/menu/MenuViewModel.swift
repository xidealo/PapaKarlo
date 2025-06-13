//
//  MenuViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared

class MenuViewModel: ObservableObject {
    @Published var menuViewState: MenuViewState = .init(
        menuItems: [],
        categoryItemModels: [],
        isLoading: true,
        scrollToHorizontalPostion: "",
        discount: nil,
        cartCount: "",
        cartCost: ""
    )
    
    var closable: Closeable? = nil

    @Published var scrollToPostion = "no pos"

    var lastDisappearIndex = 1
    var lastAppearIndex = 2
    var canCalculate = true
    let addMenuProductUseCase = iosComponent.provideAddMenuProductUseCase()

    init() {
        iosComponent.provideMenuInteractor().getMenuSectionList { menuSectionList, error in
            if error != nil {
                print("error")
                return
            }

            DispatchQueue.main.async {
                iosComponent.provideGetDiscountUseCase().invoke { discount, _ in
                    (self.menuViewState.copy() as! MenuViewState).apply { copiedState in
                        copiedState.isLoading = false
                        copiedState.menuItems = self.getMenuItems(menuSectionList: menuSectionList ?? [])
                        copiedState.categoryItemModels = self.getCategoryItems(menuSectionList: menuSectionList ?? [])
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

    func subscribe() {
        closable = iosComponent.provideObserveCartUseCase().invoke().watch { cartCostAndCount in
            self.menuViewState = MenuViewState(
                menuItems: self.menuViewState.menuItems,
                categoryItemModels: self.menuViewState.categoryItemModels,
                isLoading: self.menuViewState.isLoading,
                scrollToHorizontalPostion: self.menuViewState.scrollToHorizontalPostion,
                discount: self.menuViewState.discount,
                cartCount: cartCostAndCount?.count ?? "",
                cartCost: (cartCostAndCount?.cost ?? "") + " " + Constants().RUBLE_CURRENCY
            )
        }
    }
    
    private func getMenuItems(menuSectionList: [MenuSection]) -> [MenuItem] {
        return menuSectionList.map { menuSection in
            MenuItem(categorySectionItem: CategorySectionItem(
                id: menuSection.category.uuid,
                name: menuSection.category.name,
                menuProdctItems: menuSection.menuProductList.map { menuProduct in
                    let oldPriceInt = Int(truncating: menuProduct.oldPrice ?? 0)
                    print(oldPriceInt)
                    return MenuProductItem(
                        id: menuProduct.uuid + menuSection.category.uuid,
                        productUuid: menuProduct.uuid,
                        name: menuProduct.name,
                        newPrice: String(menuProduct.newPrice) + Strings.CURRENCY,
                        oldPrice: oldPriceInt == 0 ? nil : String(oldPriceInt) + Strings.CURRENCY,
                        photoLink: menuProduct.photoLink,
                        hasAdditions: !menuProduct.additionGroups.isEmpty
                    )
                }
            )
            )
        }
    }

    private func getCategoryItems(menuSectionList: [MenuSection]) -> [CategoryItemModel] {
        return menuSectionList.map { menuSection in
            CategoryItemModel(
                key: "MenuCategoryHeaderItemModel " + menuSection.category.uuid,
                id: menuSection.category.uuid,
                name: menuSection.category.name,
                isSelected: false
            )
        }
    }

    func addCartProductToCart(menuProductItem: MenuProductItem) {
        addMenuProductUseCase.invoke(menuProductUuid: menuProductItem.productUuid) { _ in
            print("Not added")
        }
    }

    func selectTagWithHorizontalScroll(selectIndex: Int) {
        (menuViewState.copy() as! MenuViewState).apply { newState in
            newState.categoryItemModels = newState.categoryItemModels.enumerated().map { index, categoryItem in
                if selectIndex == index {
                    newState.scrollToHorizontalPostion = categoryItem.id
                    return CategoryItemModel(
                        key: categoryItem.key,
                        id: categoryItem.id,
                        name: categoryItem.name,
                        isSelected: true
                    )
                } else {
                    return CategoryItemModel(
                        key: categoryItem.key,
                        id: categoryItem.id,
                        name: categoryItem.name,
                        isSelected: false
                    )
                }
            }
            menuViewState = newState
        }
    }

    func seletTagWithScroll(tagName: String) {
        canCalculate = false
        (menuViewState.copy() as! MenuViewState).apply { newState in
            print("seletTagWithScroll canCalculate \(canCalculate)")

            newState.categoryItemModels = newState.categoryItemModels.map { categoryItem in
                if tagName == categoryItem.name {
                    let isFirstItem = newState.categoryItemModels.firstIndex(where: { categoryItem in
                        categoryItem.name == tagName
                    }) == 0

                    if newState.discount != nil && isFirstItem {
                        scrollToPostion = DISCOUNT_ID
                        print("scroll to discount")
                    } else {
                        scrollToPostion = categoryItem.id
                        print("scroll categoryItem.id \(categoryItem.id)")
                    }

                    newState.scrollToHorizontalPostion = categoryItem.id

                    return CategoryItemModel(
                        key: categoryItem.key,
                        id: categoryItem.id,
                        name: categoryItem.name,
                        isSelected: true
                    )
                } else {
                    return CategoryItemModel(
                        key: categoryItem.key,
                        id: categoryItem.id,
                        name: categoryItem.name,
                        isSelected: false
                    )
                }
            }
            menuViewState = newState
        }
    }

    func checkAppear(index: Int) {
        let isItemWichToScrolled = scrollToPostion == menuViewState.categoryItemModels[index].id
        let isItDiscountItem = scrollToPostion == DISCOUNT_ID

        if isItemWichToScrolled || isItDiscountItem {
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(500)) {
                self.canCalculate = true
            }
        }

        if !canCalculate {
            return
        }

        lastAppearIndex = index
        if lastDisappearIndex > lastAppearIndex {
            selectTagWithHorizontalScroll(selectIndex: index)
        }
    }

    func checkDisappear(index: Int) {
        if !canCalculate {
            return
        }

        lastDisappearIndex = index

        if lastAppearIndex > lastDisappearIndex {
            selectTagWithHorizontalScroll(selectIndex: index + 1)
        }
    }
    
    func unsubFromFlows() {
        closable?.close()
        closable = nil
    }
}
