//
//  ConsumerCartView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

struct ConsumerCartView: View {
    
    let consumerCartUI : ConsumerCartUI

    var body: some View {
        VStack{
            //ConsumerCartEmptyScreen()
            ConsumerCartSuccessScreen(cartProductList: consumerCartUI.cartProductList)
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_CART_PRODUCTS)
        )
        
    }
}

struct ConsumerCartView_Previews: PreviewProvider {
    static var previews: some View {
        ConsumerCartView(consumerCartUI: ConsumerCartUI(forFreeDelivery: "100", cartProductList: [CartProductItem(id: UUID(), name: "Burger", newCost: "233", oldCost: "2223", photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg", count: 1, menuProductUuid: "uuid")], oldTotalCost: nil, newTotalCost: "200"))
    }
}

struct ConsumerCartSuccessScreen: View {
    
    let cartProductList: [CartProductItem]
    
    var body: some View {
        ScrollView {
            LazyVStack{
                ForEach(cartProductList){ cartProductItem in
                    CartProductView(cartProductItem: cartProductItem).padding(.horizontal, Diems.MEDIUM_PADDING).padding(.vertical, Diems.SMALL_PADDING)
                }
            }
        }.padding(.top, Diems.MEDIUM_PADDING)
    }
}

struct ConsumerCartEmptyScreen: View {
    var body: some View {
        VStack{
            Spacer()
            
            DefaultImage(imageName: "RunMan")
            
            Text(Strings.MSG_CART_PRODUCT_EMPTY).multilineTextAlignment(.center)
            Spacer()
            
            NavigationLink(
                destination:MenuView()
            ){
                Text(Strings.ACTION_CART_PRODUCT_MENU).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(8)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}
