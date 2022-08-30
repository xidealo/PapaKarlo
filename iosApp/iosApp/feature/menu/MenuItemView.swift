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
    let action: () -> Void
    
    var body: some View {
        HStack{
            
            KFImage(URL(string: menuProductItem.photoLink))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack{
                Text(menuProductItem.name)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                    .foregroundColor(Color("onSurface"))
                    .multilineTextAlignment(.leading)
                HStack{
                    if menuProductItem.oldPrice != nil{
                        StrikeText(text: String(menuProductItem.oldPrice ?? 0) + Strings.CURRENCY)
                    }
                    Text(menuProductItem.newPrice)
                        .frame(maxWidth:.infinity, alignment: .topLeading)
                        .foregroundColor(Color("onSurface"))
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            Button(action: action) {
                Text(Strings.ACTION_MENU_PRODUCT_WANT)
                    .frame(maxWidth:Diems.BUTTON_WIDTH, maxHeight:Diems.BUTTON_HEIGHT)
                    .padding(.vertical, 10)
                    .padding(.horizontal, 25)
                    .foregroundColor(Color("primary"))
                    .overlay(RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                        .stroke(Color("primary"), lineWidth: 2))
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                
            }.padding(.trailing, Diems.MEDIUM_PADDING)
            
        }.frame(maxWidth:.infinity, alignment: .topLeading)
            .background(Color("surface")).cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct MenuItemView_Previews: PreviewProvider {
    static var previews: some View {
        MenuItemView(menuProductItem:MenuProductItem(id: UUID().uuidString, name: "Burger sdlmdkm dkmk", newPrice: "200 R", oldPrice: 250, photoLink: "")) {
            print("btn pressed")
        }
    }
}
