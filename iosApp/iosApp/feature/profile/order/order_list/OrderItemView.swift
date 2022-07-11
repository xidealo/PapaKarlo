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
        
        HStack{
            Text(orderItem.code).foregroundColor(Color("onSurface"))
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
                .frame(width: 60)
            
            Text(orderItem.status)
                .foregroundColor(Color("surface"))
                .padding(Diems.SMALL_PADDING)
                .background(getColor(statusName: orderItem.status))
                .font(.system(size: Diems.SMALL_TEXT_SIZE, weight: .heavy, design: .default))
                .cornerRadius(Diems.MEDIUM_RADIUS)
            
            Spacer()
            Text(orderItem.dateTime).font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .thin, design: .default))
            
        }.frame(maxWidth:.infinity)
        .padding(Diems.MEDIUM_PADDING)
        .background(Color("surface"))
        .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
    
    func getColor(statusName:String) -> Color{
      switch statusName{
        case OrderStatus.notAccepted.name : return Color("notAcceptedColor")
        case OrderStatus.accepted.name : return Color("acceptedColor")
        case OrderStatus.preparing.name : return Color("preparingColor")
        case OrderStatus.sentOut.name : return Color("sentOutColor")
        case OrderStatus.done.name : return Color("doneColor")
        case OrderStatus.delivered.name : return Color("deliveredColor")
        case OrderStatus.canceled.name : return Color("canceledColor")
        default : return Color.blue
        }
    }
}

struct OrderItemView_Previews: PreviewProvider {
    static var previews: some View {
        OrderItemView(orderItem: OrderItem(id: "UUID", status: "PREPARING", code: "H-03", dateTime: "9 февраля 22:00"), destination: OrderDetailsView())
    }
}
