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
    
    init() {
        phone = "8 996 922 51 22"
        address = "CHAPAEVO 22 A"

    }
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_CAFE_OPTIONS, cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "phone", label: Strings.TITLE_CAFE_OPTIONS_CALL + " " + phone, isSystemImageName: true){
                    print("go to phone")
                }
                
                ActionCardView(icon: "CafePosition", label: Strings.TITLE_CAFE_OPTIONS_POSITION + " " + address, isSystemImageName: false){
                    print("go to map")
                }
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)    }
}

struct CafeOptionsView_Previews: PreviewProvider {
    static var previews: some View {
        CafeOptionsView()
    }
}
