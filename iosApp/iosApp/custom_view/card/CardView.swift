//
//  CardView.swift
//  iosApp
//
//  Created by Марк Шавловский on 18.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CardView: View {
    let icon:String?
    
    let label:String
    let isSystemImageName:Bool
    let isShowRightArrow:Bool
    
    init(icon:String?, label:String, isSystemImageName:Bool, isShowRightArrow:Bool) {
        self.icon = icon
        self.label = label
        self.isSystemImageName = isSystemImageName
        self.isShowRightArrow = isShowRightArrow
    }
    
    var body: some View {
        HStack(spacing:0){
            if icon != nil{
                if(isSystemImageName){
                    Image(systemName: icon ?? "")
                    .foregroundColor(
                        AppColor.onSurfaceVariant
                    )
                }else{
                    Image(icon ?? "")
                        .resizable()
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(width: 23, height: 24)
                }
            }
            
            Text(label)
                .bodyLarge()
                .lineLimit(1)
                .frame(maxWidth:.infinity, alignment: .leading)
                .foregroundColor(AppColor.onSurface)
                .multilineTextAlignment(.leading)
                .padding(.leading, Diems.MEDIUM_PADDING)

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
