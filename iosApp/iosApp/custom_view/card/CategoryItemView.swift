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
                        .labelLarge(weight: .medium)
                        .padding(.vertical, 6)
                        .padding(.horizontal, 12)
                        .background(AppColor.primary)
                        .overlay(
                            RoundedRectangle(cornerRadius: Diems.BUTTON_RADIUS)
                                .stroke(AppColor.primary, lineWidth: 4)
                        )
                        .cornerRadius(16)
                        .foregroundColor(AppColor.onPrimary)
                }
            }else{
                Button(action: {
                    action(categoryItemModel.name)
                }) {
                    Text(categoryItemModel.name)
                        .labelLarge(weight: .medium)
                        .padding(.vertical, 6)
                        .padding(.horizontal, 12)
                        .background(AppColor.surface)
                        .overlay(
                            RoundedRectangle(cornerRadius: Diems.BUTTON_RADIUS)
                                .stroke(AppColor.onSurfaceVariant, lineWidth: 4)
                        )
                        .cornerRadius(16)
                        .foregroundColor(AppColor.onSurfaceVariant)
                }
            }
        }
    }
}
