//
//  CafeOptionsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 21.04.2022.
//

import SwiftUI

struct CafeOptionsView: View {
    let phone:String
    let address:String
    let latitude:Float
    let longitude : Float
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_CAFE_OPTIONS, cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "phone", label: Strings.TITLE_CAFE_OPTIONS_CALL + " " + phone, isSystemImageName: true){
                    UIApplication.shared.open(URL(string: "tel://" + phone.removeWhitespace())!)
                }
                
                ActionCardView(icon: "CafePosition", label: Strings.TITLE_CAFE_OPTIONS_POSITION + " " + address, isSystemImageName: false){
                    
                    UIApplication.shared.open(URL(string: "http://maps.apple.com/maps?saddr=&daddr=\(latitude),\(longitude)")!)
                }
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)    }
}

struct CafeOptionsView_Previews: PreviewProvider {
    static var previews: some View {
        CafeOptionsView(phone: "8-996-922-41-86", address: "Chapaevo 22a", latitude: 0, longitude: 0)
    }
}
