//
//  OrderItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared

struct OrderItemView<Content: View>: View {
    
    let orderItem:OrderItem
    let destination:Content
    
    var body: some View {
        NavigationLink(
            destination:destination
        ){
            HStack(spacing:0){
                Text(orderItem.code)
                    .foregroundColor(Color("onSurface"))
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                
                OrderChip(orderStatus: orderItem.status)
                    .padding(.leading, Diems.SMALL_PADDING)
                
                Spacer()
                
                Text(orderItem.dateTime)
                    .font(
                        .system(
                            size: Diems.SMALL_TEXT_SIZE,
                                weight: .thin,
                                design: .default
                        )
                    )
                    .foregroundColor(Color("onSurface"))
            }.frame(maxWidth:.infinity)
                .padding(Diems.MEDIUM_PADDING)
                .background(Color("surface"))
                .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}

struct OrderItemView_Previews: PreviewProvider {
    static var previews: some View {
        OrderItemView(orderItem: OrderItem(id: "UUID", status: OrderStatus.notAccepted, code: "H-03", dateTime: "9 февраля 22:00"), destination: OrderDetailsView(orderUuid: ""))
    }
}
