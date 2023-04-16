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
                    .foregroundColor(AppColor.onSurface)
                    .titleMedium(weight: .bold)
                
                OrderChip(orderStatus: orderItem.status)
                    .padding(.leading, Diems.SMALL_PADDING)

                Spacer()
                
                Text(orderItem.dateTime)
                    .bodySmall()
                    .foregroundColor(AppColor.onSurfaceVariant)
            }.frame(maxWidth:.infinity)
                .padding(Diems.MEDIUM_PADDING)
                .background(AppColor.surface)
                .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}
