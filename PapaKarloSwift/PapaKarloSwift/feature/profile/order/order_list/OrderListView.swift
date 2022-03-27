//
//  OrderListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct OrderListView: View {
    
    let orderList : [OrderItem]
    
    var body: some View {
        VStack{
            SuccessOrderListView(orderList: orderList)
            //EmptyOrderListView()
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_MY_ORDERS)
        )
        
    }
}

struct OrderListView_Previews: PreviewProvider {
    static var previews: some View {
        OrderListView(orderList: [OrderItem(id: UUID(), status: "PREPARING", code: "S-23", dateTime: "15-05-22"),
                                  OrderItem(id: UUID(), status: "PREPARING", code: "T-03", dateTime: "15-05-22")])
    }
}

struct SuccessOrderListView: View {
    let orderList : [OrderItem]

    var body: some View {
        ScrollView {
            LazyVStack{
                ForEach(orderList){ order in
                    OrderItemView(orderItem:  order, destination: OrderDetailsView()).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                }
            }
        }.padding(.top, Diems.MEDIUM_PADDING)
    }
}



struct EmptyOrderListView: View {
    var body: some View {
        VStack{
            Spacer()
            
            DefaultImage(imageName: "EmptyPage")

            Text(Strings.MSG_ORDER_LIST_EMPTY_ORDERS).multilineTextAlignment(.center)
            Spacer()
            
        }
    }
}
