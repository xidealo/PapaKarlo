//
//  NavigationIconCardWithDivider.swift
//  iosApp
//
//  Created by Марк Шавловский on 28.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct NavigationIconCardWithDivider<Content: View>: View {
    
    let icon : String?
    
    let label : String
    let destination : Content
    var isSystem = true
    
    var body: some View {
        NavigationLink(
            destination: destination
        ){
            VStack(spacing:0){
                HStack(spacing:0){
                    if let notNullIcon = icon {
                        if(isSystem){
                            Image(
                                systemName: notNullIcon)
                            .resizable()
                            .renderingMode(.template)
                            .frame(width: 24, height: 24)
                            .foregroundColor(AppColor.onSurfaceVariant)
                        }else{
                            IconImage(
                                width: 24,
                                height: 24,
                                imageName: notNullIcon
                            )
                            .foregroundColor(AppColor.onSurfaceVariant)
                        }
                    }
                    if(icon == nil){
                        Text(label)
                            .bodyLarge()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .foregroundColor(AppColor.onSurface)
                    }else{
                        Text(label)
                            .bodyLarge()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .foregroundColor(AppColor.onSurface)
                            .padding(.leading, Diems.MEDIUM_PADDING)
                    }
                    Image(systemName:"chevron.right")
                        .foregroundColor(AppColor.onSurfaceVariant)
                    
                }.frame(maxWidth:.infinity)
                    .padding(16)
                    .background(AppColor.surface)
                
                FoodDeliveryDivider()
                    .padding(.horizontal, 16)
            }
        }
    }
}
