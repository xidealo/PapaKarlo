//
//  CategoryItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.04.2022.
//

import SwiftUI

struct CategoryItemView: View {
    
    let categoryItemModel:CategoryItemModel
    let action: (_ tagName:String) -> Void
    
    var body: some View {
        HStack(spacing:0){
            if(categoryItemModel.isSelected){
                Button(action:{
                    action(categoryItemModel.name)
                }) {
                    Text(categoryItemModel.name)
                        .padding(Diems.SMALL_PADDING)
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .foregroundColor(Color("onPrimary"))
                }
            }else{
                Button(action: {
                    action(categoryItemModel.name)
                }) {
                    Text(categoryItemModel.name)
                        .padding(Diems.SMALL_PADDING)
                        .background(Color("surface"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .foregroundColor(Color("onSurface"))
                }
            }
        }
    }
}
