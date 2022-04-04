//
//  MenuItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import SwiftUI

struct MenuItemView: View {
    
    let menuProductItem:MenuProductItem
    
    var body: some View {
        
        HStack{
            Image(uiImage: menuProductItem.photoLink.load())
                .resizable()
                .scaledToFit()
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack{
                Text(menuProductItem.name)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                    .foregroundColor(Color("onSurface"))
                HStack{
                    if menuProductItem.oldPrice != nil{
                        StrikeText(text: menuProductItem.oldPrice ?? "")
                    }
                    Text(menuProductItem.newPrice)
                        .frame(maxWidth:.infinity, alignment: .topLeading)
                        .foregroundColor(Color("onSurface"))
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            Button(action: {
                print("button pressed")
            }) {
                Text(Strings.ACTION_MENU_PRODUCT_WANT)
                    .frame(maxWidth:Diems.BUTTON_WIDTH, maxHeight:Diems.BUTTON_HEIGHT)
                    .padding()
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
        MenuItemView(menuProductItem:MenuProductItem(id: UUID().uuidString, name: "Burger", newPrice: "200 R", oldPrice: "250 R", photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg"))
    }
}

