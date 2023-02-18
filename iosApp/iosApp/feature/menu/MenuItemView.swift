//
//  MenuItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI
import Kingfisher

struct MenuItemView: View {
    
    let menuProductItem:MenuProductItem
    
    //for back after createOrder
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    
    let action: () -> Void
    
    var body: some View {
        ZStack(alignment: .trailing){
            NavigationLink(
                destination:
                ProductDetailsView(
                    menuProductUuid: menuProductItem.productUuid,
                    isRootActive: self.$isRootActive,
                    selection: self.$selection,
                    showOrderCreated: $showOrderCreated
                )
            ){
                HStack(spacing:0){
                    KFImage(URL(string: menuProductItem.photoLink))
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(
                            maxWidth: Diems.IMAGE_ELEMENT_WIDTH,
                            maxHeight: Diems.IMAGE_ELEMENT_HEIGHT
                        )
                    
                    VStack(spacing:0){
                        Text(menuProductItem.name)
                            .frame(maxWidth:.infinity, alignment: .topLeading)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                            .foregroundColor(Color("onSurface"))
                            .padding(.top, Diems.SMALL_PADDING)
                            .multilineTextAlignment(.leading)
                        HStack(spacing:0){
                            if menuProductItem.oldPrice != nil{
                                StrikeText(text: String(menuProductItem.oldPrice ?? 0) + Strings.CURRENCY)
                                    .padding(.trailing, Diems.SMALL_PADDING)
                            }
                            Text(menuProductItem.newPrice)
                                .frame(maxWidth:.infinity, alignment: .topLeading)
                                .foregroundColor(Color("onSurface"))
                        }
                        Spacer()
                    }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
                        .padding(.leading, Diems.SMALL_PADDING)
                    Button(action: action) {
                        Text(Strings.ACTION_MENU_PRODUCT_WANT)
                            .frame(maxWidth:Diems.BUTTON_WIDTH, maxHeight:Diems.BUTTON_HEIGHT)
                            .padding(.vertical, 10)
                            .padding(.horizontal, 25)
                            .foregroundColor(Color("primary"))
                            .overlay(RoundedRectangle(cornerRadius: Diems.BUTTON_RADIUS)
                                .stroke(Color("primary"), lineWidth: 2))
                            .padding(.leading, Diems.HALF_SMALL_PADDING)
                    }.padding(.trailing, Diems.MEDIUM_PADDING)
                    
                }.frame(maxWidth:.infinity, alignment: .topLeading)
                    .background(Color("surface"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
           
            }.isDetailLink(false)
        }
    }
}
