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
            .categorySectionItem(
                CategorySectionItem(
                    id: UUID(),
                    name: "Burger"
                )
            )
        ]
    }
    
    var body: some View {
        VStack{
            ScrollView {
                LazyVStack{
                    ForEach(menuItems){ menuItem in
                        
                        switch menuItem {
                            case .categorySectionItem(let categorySection):
                                LargeHeaderText(text: categorySection.name)
                            case .menuProductItem(let menuProductItem):
                                MenuItemView(menuProductItem:menuProductItem).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
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
