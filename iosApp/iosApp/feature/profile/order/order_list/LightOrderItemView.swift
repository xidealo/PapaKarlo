//
//  LightOrderItemView.swift
//  iosApp
//
//  Created by Марк Шавловский on 18.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LightOrderItemView<Content: View>: View{
    
    let lightOrder:LightOrder
    let destination:Content
    
    var body: some View {
        NavigationLink(
            destination:destination
        ){
            HStack(spacing:0){
                Text(lightOrder.code)
                    .foregroundColor(Color("onSurface"))
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                
                OrderChip(orderStatus: lightOrder.status)
                    .padding(.leading, Diems.SMALL_PADDING)
                
                Spacer()
                
                Text(dateUtil.getDateTimeString(dateTime: lightOrder.dateTime))
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
