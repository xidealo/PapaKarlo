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
            MenuItem(categorySectionItem:  CategorySectionItem(
                id: UUID(),
                name: "Burger",
                menuProdctItems: [
                    MenuProductItem(id: UUID(),
                                    name: "Burger",
                                    newPrice: "200 R",
                                    oldPrice: "250 R",
                                    photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg")
                ]
            ))
        ]
    }
    
    var body: some View {
        VStack{
            ScrollView {
                LazyVStack{
                    ForEach(menuItems){ menuItem in
                        Section(header: LargeHeaderText(text: menuItem.categorySectionItem.name)){
                            ForEach(menuItem.categorySectionItem.menuProdctItems){ menuProductItem in
                                MenuItemView(menuProductItem: menuProductItem).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                            }
                        }
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING).navigationTitle(
                Text(Strings.TITLE_MENU)
            )
        }
        
        
    }
}
//
//

struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
