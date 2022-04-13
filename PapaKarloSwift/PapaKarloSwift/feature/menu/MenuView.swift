//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    let menuUI:MenuUI
    
    init() {
        menuUI = MenuUI(menuItems: [
            MenuItem(id: UUID().uuidString, categorySectionItem:  CategorySectionItem(
                id: UUID().uuidString,
                name: "Burger",
                menuProdctItems: [
                    MenuProductItem(id: UUID().uuidString,
                                    name: "Burger",
                                    newPrice: "200 R",
                                    oldPrice: "250 R",
                                    photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg")
                ]
            )),
            MenuItem(id: UUID().uuidString, categorySectionItem:  CategorySectionItem(
                id: UUID().uuidString,
                name: "CArtoxa",
                menuProdctItems: [
                    MenuProductItem(id: UUID().uuidString,
                                    name: "Cartoxa",
                                    newPrice: "100 R",
                                    oldPrice: "150 R",
                                    photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg"),
                    MenuProductItem(id: UUID().uuidString,
                                    name: "Cartoxa",
                                    newPrice: "100 R",
                                    oldPrice: "150 R",
                                    photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg")
                ]
            ))
        ], categoryItemModels: [
            CategoryItemModel(key: "", id: "1", name: "Burgers", isSelected: true),
            CategoryItemModel(key: "", id: "2", name: "Pizza", isSelected: false),
            CategoryItemModel(key: "", id: "3", name: "Potato", isSelected: false),
            CategoryItemModel(key: "", id: "4", name: "Potato", isSelected: false),
            CategoryItemModel(key: "", id: "5", name: "Potato", isSelected: false),
            CategoryItemModel(key: "", id: "6", name: "Potato", isSelected: false)
        ])
    }
    
    var body: some View {
        
        VStack(spacing:0){
            ToolbarView(title: Strings.TITLE_MENU, cost: "220 R", count: "2",  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            
            ScrollView(.horizontal, showsIndicators:false) {
                HStack{
                    ForEach(menuUI.categoryItemModels){ categoryItemModel in
                        CategoryItemView(categoryItemModel: categoryItemModel)
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
            
            ScrollView {
                LazyVStack{
                    ForEach(menuUI.menuItems){ menuItem in
                        Section(header: LargeHeaderText(text: menuItem.categorySectionItem.name)){
                            ForEach(menuItem.categorySectionItem.menuProdctItems){ menuProductItem in
                                NavigationLink(
                                    destination:ProductDetailsView(menuProductUuid: menuProductItem.id)
                                ){
                                    MenuItemView(menuProductItem: menuProductItem).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                                }
                            }
                        }
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
        }
        .background(Color("background"))
        .navigationBarHidden(true)
    }
}


struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
