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
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleCafeOptions",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                ActionCardView(
                    icon: "PhoneIcon",
                    label: Strings.TITLE_CAFE_OPTIONS_CALL + ": " + phone,
                    isSystemImageName: false,
                    isShowRightArrow: true
                ){
                    UIApplication.shared.open(URL(string: "tel://" + phone.removeWhitespace())!)
                }
                
                ActionCardView(
                    icon: "CafePosition",
                    label: Strings.TITLE_CAFE_OPTIONS_POSITION + ": " + address,
                    isSystemImageName: false,
                    isShowRightArrow: true
                ){
                    UIApplication.shared.open(URL(string: "http://maps.apple.com/maps?saddr=&daddr=\(latitude),\(longitude)")!)
                }
                .padding(.top, Diems.SMALL_PADDING)
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()

    }
}

struct CafeOptionsView_Previews: PreviewProvider {
    static var previews: some View {
        CafeOptionsView(
            phone: "8-996-922-41-86", address: "Chapaevo 22a", latitude: 0, longitude: 0
        )
    }
}
