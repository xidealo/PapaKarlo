//
//  CardView.swift
//  iosApp
//
//  Created by Марк Шавловский on 18.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CardView: View {
    let icon : String?
    
    let label : String
    let isSystemImageName : Bool
    let isShowRightArrow : Bool
    
    var placeHolder:LocalizedStringKey? = nil

    var body: some View {
        HStack(spacing:0){
            if let notNullIcon = icon {
                if(isSystemImageName){
                    Image(systemName: notNullIcon)
                    .foregroundColor(
                        AppColor.onSurfaceVariant
                    )
                }else{
                    Image(notNullIcon)
                        .resizable()
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(width: 23, height: 24)
                }
            }
            
            VStack(spacing:0){
                if let placeHolder = placeHolder {
                    Text(placeHolder)
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth:.infinity, alignment: .leading)
                        .padding(.leading, Diems.MEDIUM_PADDING)
                        .padding(.bottom, 2)
                }
         
                Text(label)
                    .bodyLarge()
                    .lineLimit(1)
                    .frame(maxWidth:.infinity, alignment: .leading)
                    .foregroundColor(AppColor.onSurface)
                    .multilineTextAlignment(.leading)
                    .padding(.leading, (icon != nil) ? Diems.MEDIUM_PADDING : 0)
            }
            .frame(maxWidth:.infinity, alignment: .leading)
            
            if isShowRightArrow{
                Image(systemName:"chevron.right")
                    .foregroundColor(AppColor.onSurfaceVariant)
            }
            
        }.frame(maxWidth:.infinity)
        .padding(.vertical, 12)
        .padding(.horizontal, 12)
        .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
