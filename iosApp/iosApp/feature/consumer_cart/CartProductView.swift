//
//  CartProductView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI
import Kingfisher

struct CartProductView: View {
    
    let cartProductItem:CartProductItem
    let plusAction: () -> Void
    let minusAction: () -> Void
    
    var body: some View {
        HStack(spacing:0){
            KFImage(URL(string: cartProductItem.photoLink))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack(spacing:0){
                Text(cartProductItem.name)
                    .titleSmall(weight: .bold)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .foregroundColor(AppColor.onSurface)
                    .padding(.top, 8)

                HStack(spacing:0){
                    if let oldCost = cartProductItem.oldCost {
                        Text(String(oldCost) + Strings.CURRENCY)
                            .strikethrough()
                            .bodySmall()
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                    
                    Text(cartProductItem.newCost)
                        .bodySmall(weight: .bold)
                        .frame(maxWidth:.infinity, alignment: .topLeading)
                        .foregroundColor(AppColor.onSurface)
                        .padding(.leading, (cartProductItem.oldCost != nil) ? 4 : 0)
                }
                .padding(.top, 4)
            }
            .frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT, alignment: .topLeading)
            .padding(.leading, Diems.SMALL_PADDING)
            
            CountPicker(
                count: String(cartProductItem.count),
                plusAction: plusAction,
                minusAction: minusAction
            )
                .padding(.trailing, Diems.SMALL_PADDING)
        }.frame(maxWidth:.infinity, alignment: .topLeading)
            .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
