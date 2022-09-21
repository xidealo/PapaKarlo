//
//  CategoryItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

struct CategoryItemView: View {
    
    let categoryItemModel:CategoryItemModel
    
    var body: some View {
        HStack{
            if(categoryItemModel.isSelected){
                Button(action: {
                    print("button pressed")
                }) {
                    Text(categoryItemModel.name)
                        .padding(Diems.SMALL_PADDING)
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .foregroundColor(Color("onPrimary"))
                }
            }else{
                Button(action: {
                    print("button pressed")
                }) {
                    Text(categoryItemModel.name)
                        .padding(Diems.SMALL_PADDING)
                        .background(Color("surface"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .foregroundColor(Color("onSurface"))
                }
            }
        }.padding(.leading, Diems.HALF_SMALL_PADDING)
    }
}

struct CategoryItemView_Previews: PreviewProvider {
    static var previews: some View {
        CategoryItemView(categoryItemModel: CategoryItemModel(key: "", id: "", name: "Burger", isSelected: true))
    }
}
