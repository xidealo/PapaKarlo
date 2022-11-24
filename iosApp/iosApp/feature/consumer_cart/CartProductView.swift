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
            
            VStack{
                Text(cartProductItem.name)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                    .foregroundColor(Color("onSurface"))

                HStack{
                    if cartProductItem.oldCost != nil {
                        StrikeText(text: String(cartProductItem.oldCost!) + Strings.CURRENCY)
                    }
                    Text(cartProductItem.newCost)
                        .frame(maxWidth:.infinity, alignment: .topLeading)
                        .foregroundColor(Color("onSurface"))
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT).padding(.leading, Diems.SMALL_PADDING)
            
            CountPicker(count: String(cartProductItem.count), plusAction: plusAction, minusAction: minusAction).padding(.trailing, Diems.SMALL_PADDING)
        }.frame(maxWidth:.infinity, alignment: .topLeading)
        .background(Color("surface"))
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
