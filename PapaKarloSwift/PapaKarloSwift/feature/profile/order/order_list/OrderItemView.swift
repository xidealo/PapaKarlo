//
//  OrderItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct OrderItemView: View {
    
    
    let orderItem:OrderItem
    
    var body: some View {
        HStack{
            Text(orderItem.code).foregroundColor(Color("onSurface"))
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
            
            Text(orderItem.status)
                .foregroundColor(Color("surface"))
                .padding(Diems.SMALL_PADDING)
                .background(Color.blue)
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
                .cornerRadius(Diems.MEDIUM_RADIUS)
            Spacer()
            Text(orderItem.dateTime).font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .thin, design: .default))
            
        }.frame(maxWidth:.infinity)
        .padding(Diems.MEDIUM_PADDING)
        .background(Color("surface"))

    }
}

struct OrderItemView_Previews: PreviewProvider {
    static var previews: some View {
        OrderItemView(orderItem: OrderItem(id: UUID(), status: "PREPARING", code: "H-03", dateTime: "9 февраля 22:00"))
    }
}
