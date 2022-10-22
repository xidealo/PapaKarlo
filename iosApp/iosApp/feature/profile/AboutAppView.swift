//
//  AboutAppView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared

struct AboutAppView: View {
    
    let version:String
    
    init(){
        version = "1.0.0"
    }
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(title: Strings.TITLE_ABOUT_APP, cost: "220 R", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack(spacing:0){
                ActionCardView(icon: "DeveloperIcon", label: Strings.TITLE_ABOUT_APP_DEVELOPER, isSystemImageName: false, isShowRightArrow: true){
                    UIApplication.shared.open(URL(string: Constants.init().BB_VK_LINK)!)
                }
                
                CardView(icon: "VersionIcon", label: Strings.TITLE_ABOUT_APP_VERSION + " " + version, isSystemImageName: false, isShowRightArrow: false)
                    .padding(.top, Diems.SMALL_PADDING)
                
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)
    }
}

struct AboutAppView_Previews: PreviewProvider {
    static var previews: some View {
        AboutAppView()
    }
}
