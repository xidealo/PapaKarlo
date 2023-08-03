//
//  ActionTextCardView.swift
//  iosApp
//
//  Created by Марк Шавловский on 14.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ActionTextCardView: View {
    
    let placeHolder:String
    let text:String
    let action: () -> Void
    
    var body: some View {
        Button(action: action){
            HStack(spacing:0){
                VStack(spacing:0){
                    Text(placeHolder)
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth:.infinity, alignment: .leading)
                    
                    Text(text)
                        .bodyMedium()
                        .frame(maxWidth:.infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurface)
                        .multilineTextAlignment(.leading)
                }
                .frame(maxWidth:.infinity, alignment: .leading)
                
                Image(systemName:"chevron.right")
                    .foregroundColor(AppColor.onSurfaceVariant)
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.vertical, Diems.SMALL_PADDING)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}



struct ActionLocalizedTextCardView: View {
    
    let placeHolder:LocalizedStringKey
    let text:LocalizedStringKey
    let action: () -> Void
    
    var body: some View {
        Button(action: action){
            HStack(spacing:0){
                VStack(spacing:0){
                    Text(placeHolder)
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth:.infinity, alignment: .leading)
                    
                    Text(text)
                        .bodyMedium()
                        .frame(maxWidth:.infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurface)
                        .multilineTextAlignment(.leading)
                }
                .frame(maxWidth:.infinity, alignment: .leading)
                
                Image(systemName:"chevron.right")
                    .foregroundColor(AppColor.onSurfaceVariant)
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.vertical, Diems.SMALL_PADDING)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}
