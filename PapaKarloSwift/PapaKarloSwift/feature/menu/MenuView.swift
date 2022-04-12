//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    let menuItems : [MenuItem]
    
    init() {
        menuItems = [
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
        ]
    }
    
    var body: some View {
        
        VStack{
            ToolbarView(title: Strings.TITLE_MENU, cost: "220 R", count: "2",  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            LazyVStack{
                ForEach(menuItems){ menuItem in
                    
                }
            
            
            ScrollView {
                LazyVStack{
                    ForEach(menuItems){ menuItem in
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
            .background(Color("background"))
            .navigationBarHidden(true)
        }
    }
}


struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
