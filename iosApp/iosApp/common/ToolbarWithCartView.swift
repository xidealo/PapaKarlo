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
                    HStack{
                        Text(cost)
                            .foregroundColor(Color("onSurface"))
                        
                        Image(systemName: "cart").foregroundColor(Color("onSurface"))
                        Text(count)
                            .foregroundColor(Color("onPrimary"))
                            .padding(4)
                            .background(Color("primary"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .padding(.bottom, Diems.SMALL_PADDING)
                            .padding(.leading, -12)
                            .font(.system(size: Diems.SMALL_TEXT_SIZE, design: .default))
                    }
                }
                .isDetailLink(false)
                .padding(.vertical, Diems.SMALL_PADDING)
                .padding(.trailing, Diems.SMALL_PADDING)
                
            }.background(Color("surface"))
            
            if(isShowLogo){
                Image("LoginLogo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(height: 40)
            }
        }
        
       
    }
}
