//
//  ToolbarView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.04.2022.
//

import SwiftUI
import shared

struct ToolbarView: View {
    
    let title:String
    let cost:String
    let count:String
    
    let isCartVisible:Bool
    
    var logout: (() -> Void)? = nil
    var back: (() -> Void)? = nil
    
    var body: some View {
        HStack(spacing:0){
            Button(action: {
                back!()
            }) {
                if(back != nil){
                    Image(systemName: "arrow.backward")
                        .foregroundColor(Color("onSurface"))
                        .padding(.horizontal, Diems.SMALL_PADDING)
                }
            }.padding(Diems.SMALL_PADDING)
            
            
            Text(title)
                .foregroundColor(Color("onSurface"))
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .bold, design: .default))
                .padding(.vertical, Diems.MEDIUM_PADDING)
            Spacer()
            
            if logout != nil{
                Button(action:{
                    logout!()
                }){
                    Image("LogoutIcon")
                        .resizable()
                        .frame(width: 24, height: 24)
                        .padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurface"))
                }
            }
            
            if(isCartVisible){
                NavigationLink(
                    destination:consumerCartView
                ){
                    HStack{
                        Text(cost).foregroundColor(Color("onSurface"))
                        
                        Image(systemName: "cart").foregroundColor(Color("onSurface"))
                        Text(count).foregroundColor(Color("colorOnPrimary"))
                            .padding(3)
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
            }
        }.background(Color("surface"))
    }
}

struct ToolbarView_Previews: PreviewProvider {
    static var previews: some View {
        ToolbarView(title: "Title", cost: "220R", count: "2", isCartVisible: false)
    }
}
