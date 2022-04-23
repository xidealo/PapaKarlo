//
//  ToolbarView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.04.2022.
//

import SwiftUI

struct ToolbarView: View {
    
    let title:String
    let cost:String
    let count:String
    
    let isShowBackArrow:Bool
    let isCartVisible:Bool
    let isLogoutVisible:Bool
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        HStack{
            
            Button(action: {
                self.mode.wrappedValue.dismiss()
            }) {
                if(isShowBackArrow){
                    Image(systemName: "arrow.backward").foregroundColor(Color("onSurface"))
                }
            }.padding(Diems.SMALL_PADDING)
            
            Text(title).foregroundColor(Color("onSurface"))
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .bold, design: .default))
                .padding(.vertical, Diems.MEDIUM_PADDING)
            Spacer()
          
            if(isCartVisible){
                NavigationLink(
                    destination:ConsumerCartView()
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
                }.padding(.vertical, Diems.SMALL_PADDING)
                .padding(.trailing, Diems.SMALL_PADDING)
            }
            
        }.background(Color("surface"))
    }
}

struct ToolbarView_Previews: PreviewProvider {
    static var previews: some View {
        ToolbarView(title: "Title", cost: "220R", count: "2", isShowBackArrow: true, isCartVisible: true, isLogoutVisible: false)
    }
}
