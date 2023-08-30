//
//  ToolbarWithCartView.swift
//  iosApp
//
//  Created by Марк Шавловский on 21.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ToolbarWithCartView: View {
    
    let title:LocalizedStringKey
    let titleString:String? = nil
    
    let cost:String
    let count:String
    @Binding var isShowLogo: Bool
    
    var logout: (() -> Void)? = nil
    var back: (() -> Void)? = nil
    
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    
    var body: some View {
        ZStack{
            HStack(spacing:0){
                
                ToolbarView(title: title, back: back)
            
                NavigationLink(
                    destination:ConsumerCartView(
                        isRootActive: self.$isRootActive,
                        selection: self.$selection,
                        showOrderCreated: $showOrderCreated
                    ),
                    isActive: $isRootActive
                ){
                    HStack(spacing:0){
                        Text(cost + Strings.CURRENCY)
                            .foregroundColor(AppColor.onSurface)
                        
                        Image(systemName: "cart")
                            .foregroundColor(AppColor.onSurface)
                        
                        Text(count)
                            .labelSmall(weight: .medium)
                            .foregroundColor(AppColor.onPrimary)
                            .padding(.horizontal, 4)
                            .padding(.vertical, 2)
                            .background(AppColor.primary)
                            .cornerRadius(Diems.LARGE_RADIUS)
                            .padding(.bottom, 12)
                            .padding(.leading, -8)
                    }
                }
                .isDetailLink(false)
                .padding(.vertical, Diems.SMALL_PADDING)
                .padding(.trailing, Diems.SMALL_PADDING)
                
            }.background(AppColor.surface)
            
            if(isShowLogo){
                Image("LoginLogo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(height: 40)
            }
        }
        
       
    }
}
