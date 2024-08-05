//
//  LightOrderItemView.swift
//  iosApp
//
//  Created by Марк Шавловский on 18.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LightOrderItemView<Content: View>: View {
    
    let lightOrder: LightOrder
    let destination: Content
    
    var body: some View {
        NavigationLink(
            destination: destination
        ){
            HStack(spacing:0){
                Text(lightOrder.code)
                    .foregroundColor(AppColor.onSurface)
                    .titleMedium(weight: .bold)
                
                OrderChip(orderStatus: lightOrder.status)
                    .padding(.leading, Diems.SMALL_PADDING)
                
                Spacer()
                
                Text(dateUtil.getDateTimeString(dateTime: lightOrder.dateTime))
                    .bodySmall()
                    .foregroundColor(AppColor.onSurfaceVariant)
            }.frame(maxWidth:.infinity)
                .padding(12)
                .background(AppColor.surface)
                .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}
