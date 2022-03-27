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
            ConsumerCartSuccessScreen(consumerCartUI: consumerCartUI)
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_CART_PRODUCTS)
        )
        
    }
}

struct ConsumerCartView_Previews: PreviewProvider {
    static var previews: some View {
        ConsumerCartView(consumerCartUI: ConsumerCartUI(forFreeDelivery: "100", cartProductList: [CartProductItem(id: UUID(), name: "Burger", newCost: "233", oldCost: "2223", photoLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg", count: 1, menuProductUuid: "uuid")], oldTotalCost: "234", newTotalCost: "200"))
    }
}

struct ConsumerCartSuccessScreen: View {
    
    let consumerCartUI : ConsumerCartUI

    var body: some View {
        VStack(spacing:0){
            ZStack(alignment: .bottom){
                LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom).frame(height:20)
                
                ScrollView {
                    LazyVStack{
                        ForEach(consumerCartUI.cartProductList){ cartProductItem in
                            CartProductView(cartProductItem: cartProductItem).padding(.horizontal, Diems.MEDIUM_PADDING).padding(.vertical, Diems.SMALL_PADDING)
                        }
                    }
                }
            }
           
            VStack{
                
                HStack{
                    BoldText(text: Strings.MSG_CART_PRODUCT_RESULT)
                    Spacer()
                    
                    if consumerCartUI.oldTotalCost != nil{
                        StrikeText(text: consumerCartUI.oldTotalCost ?? "0")
                    }
                    BoldText(text: consumerCartUI.newTotalCost)
                }.padding()

                
                NavigationLink(
                    destination:CreateOrderView()
                ){
                    Text(Strings.ACTION_CART_PRODUCT_CREATE_ORDER).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(8)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }.padding(Diems.MEDIUM_PADDING)
            }.background(Color("surface"))
        }
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
