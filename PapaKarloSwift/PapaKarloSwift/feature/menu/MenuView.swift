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
                            case .categorySectionItem:
                                Text("Title")
                            case .menuProductItem:
                                Text("body")
                        }
                        
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
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
