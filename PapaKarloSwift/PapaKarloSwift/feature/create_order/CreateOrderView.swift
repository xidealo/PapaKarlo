//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI

struct CreateOrderView: View {
    private let orderCreationUI:OrderCreationUI
    
    init() {
        orderCreationUI = OrderCreationUI(isDelivery: true, address: "2222sdas f", comment: nil, deferredTime: "22:00", totalCost: "223", deliveryCost: "100", amountToPay: "323", isLoading: false)
    }
    var body: some View {
        VStack(spacing: 0){
            VStack{
                if orderCreationUI.address == nil{
                    NavigationCardView(icon: nil, label: Strings.HINT_CREATION_ORDER_ADDRESS, destination: CreateAddressView())
                }else{
                    NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_ADDRESS, text: orderCreationUI.address!, destination:UserAddressListView())
                }
                
                if orderCreationUI.comment == nil{
                    NavigationCardView(icon: nil, label: Strings.HINT_CREATION_ORDER_COMMENT, destination: CreateAddressView())
                }else{
                    NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_COMMENT, text: orderCreationUI.comment!, destination:UserAddressListView())
                }
                
                
                NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_DEFERRED_TIME, text: orderCreationUI.deferredTime, destination:UserAddressListView())
            }.padding(Diems.MEDIUM_PADDING)
           
            
            Spacer()
            LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom).frame(height:20)
            VStack{
                HStack{
                    Text(Strings.MSG_CREATION_ORDER_RESULT)
                    Spacer()
                    Text(orderCreationUI.totalCost)
                }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                
                HStack{
                    Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                    Spacer()
                    Text(orderCreationUI.deliveryCost)
                }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                HStack{
                    BoldText(text:Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                    Spacer()
                    BoldText(text:orderCreationUI.amountToPay)
                }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                
                NavigationLink(
                    destination:CreateOrderView()
                ){
                    Text(Strings.ACTION_CART_PRODUCT_CREATE_ORDER).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }.padding(Diems.MEDIUM_PADDING)
            }.background(Color("surface"))
            
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .navigationTitle(Text(Strings.TITLE_SETTINGS))
        .navigationBarItems(leading: Image("chevron.forward.square"))
    }
}

struct CreateOrderView_Previews: PreviewProvider {
    static var previews: some View {
        CreateOrderView()
    }
}
