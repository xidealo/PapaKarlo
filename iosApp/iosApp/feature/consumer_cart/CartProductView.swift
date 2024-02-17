//
//  CartProductView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI
import Kingfisher
import shared

struct CartProductView: View {
    
    let cartProductItem: CartProductItem
    
    // Navigation to ProductDetails
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    @State var openProductDetails:Bool = false
    @Binding var created:Bool
    @Binding var edited:Bool
    
    // ----
    
    // Actions
    let plusAction: () -> Void
    let minusAction: () -> Void
    // ----
    
    var body: some View {
        NavigationLink(
            destination:
                ProductDetailsView(
                    menuProductUuid: cartProductItem.menuProductUuid,
                    menuProductName: cartProductItem.name,
                    cartProductUuid: cartProductItem.uuid,
                    additionUuidList: cartProductItem.additionUuidList,
                    productDetailsOpenedFrom: ProductDetailsOpenedFrom.cartProduct,
                    isRootActive: self.$isRootActive,
                    selection: self.$selection,
                    showOrderCreated: $showOrderCreated,
                    created: $created,
                    edited: $edited
                ),
            isActive: $openProductDetails
        ){
            HStack(spacing:0){
                KFImage(URL(string: cartProductItem.photoLink))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                
                VStack(spacing:0){
                    Text(cartProductItem.name)
                        .titleSmall(weight: .bold)
                        .lineLimit(nil)
                        .frame(maxWidth:.infinity, alignment: .topLeading)
                        .foregroundColor(AppColor.onSurface)
                    
                    if let additions = cartProductItem.additions{
                        Text(additions)
                            .bodySmall()
                            .lineLimit(nil)
                            .frame(maxWidth:.infinity, alignment: .topLeading)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .padding(.top, 4)
                    }
                    
                    HStack(alignment: .top, spacing:0){
                        if let oldCost = cartProductItem.oldCost {
                            Text(oldCost)
                                .strikethrough()
                                .bodySmall()
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }
                        
                        Text(cartProductItem.newCost)
                            .bodySmall(weight: .bold)
                            .frame(maxWidth:.infinity, alignment: .topLeading)
                            .foregroundColor(AppColor.onSurface)
                            .padding(.leading, (cartProductItem.oldCost != nil) ? 4 : 0)
                        
                        CountPicker(
                            count: String(cartProductItem.count),
                            plusAction: plusAction,
                            minusAction: minusAction
                        )
                    }
                    .padding(.top, 4)
                    
                }
                .frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT, alignment: .topLeading)
                .padding(.leading, Diems.SMALL_PADDING)
            }.frame(maxWidth:.infinity, alignment: .topLeading)
        }.isDetailLink(false)
    }
}
