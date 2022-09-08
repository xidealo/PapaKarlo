//
//  OrderDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI

struct OrderDetailsView: View {

    @ObservedObject private var viewModel : OrderDetailsViewModel

    init(orderUuid:String){
        viewModel = OrderDetailsViewModel(uuid: orderUuid)
    }
    
    var body: some View {
        VStack{
            ToolbarView(title: "Number", cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            Spacer()
            StatusListView()
            
            OrderDetailsTextView(orderDetails: viewModel.orderDetailsViewState)
            Spacer()

            VStack{
                HStack{
                    BoldText(text: Strings.MSG_CART_PRODUCT_RESULT)
                    Spacer()
                    
                   // BoldText(text: consumerCartUI.newTotalCost)
                }.padding()
            
            }.background(Color("surface"))
            
        }.background(Color("background"))
            .hiddenNavigationBarStyle()
    }
}

struct StatusListView: View {
    var body: some View {
        HStack{
            
        }
    }
}

struct OrderDetailsTextView: View {
    
    let orderDetails:OrderDetailsViewState
    
    var body: some View {
        VStack{
            VStack{
                PlaceholderText(text: "Время заказа")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text("Время заказа")
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            HStack{
                VStack{
                    PlaceholderText(text: "Способ получения")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("Способ получения")
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .padding(.top, Diems.HALF_SMALL_PADDING)
                VStack{
                    PlaceholderText(text: "Ко времени")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("Ко времени")
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .padding(.top, Diems.HALF_SMALL_PADDING)
            }
            VStack{
                PlaceholderText(text: "Адрес")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text("Адрес")
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)

            VStack{
                PlaceholderText(text: "Комментарий")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text("Комментарий")
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
        }.frame(maxWidth: .infinity,
                alignment: .leading)
            .padding(Diems.MEDIUM_PADDING)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .padding(Diems.MEDIUM_PADDING)
    }
}

struct OrderDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        OrderDetailsView(orderUuid: "")
    }
}
