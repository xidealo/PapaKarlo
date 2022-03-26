//
//  CartProductView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI

struct CartProductView: View {
    
    let cartProductItem:CartProductItem
    
    var body: some View {
        
        HStack{
            Image(uiImage: cartProductItem.photoLink.load())
                .resizable()
                .scaledToFit()
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack{
                Text(cartProductItem.name)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                HStack{
                    if cartProductItem.oldCost != nil{
                        StrikeText(text: cartProductItem.oldCost ?? "")
                    }
                    Text(cartProductItem.newCost).frame(maxWidth:.infinity, alignment: .topLeading)
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            CountPicker(count: "2").padding(.trailing, Diems.SMALL_PADDING)
            
        }.frame(maxWidth:.infinity, alignment: .topLeading)
        .background(Color("surface"))
        .cornerRadius(Diems.MEDIUM_RADIUS)

    }
}

struct CartProductView_Previews: PreviewProvider {
    static var previews: some View {
        CartProductView(cartProductItem: CartProductItem(id: UUID(), name: "Burger", newCost: "233", oldCost: "2223", photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg", count: 1, menuProductUuid: "uuid"))
    }
}
