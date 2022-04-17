//
//  AboutAppView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct AboutAppView: View {
    
    let version:String
    
    init(){
        version = "1.0.0"
    }
    
    var body: some View {
        
        VStack{
            ToolbarView(title: Strings.TITLE_ABOUT_APP, cost: "220 R", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "DeveloperIcon", label: Strings.TITLE_ABOUT_APP_DEVELOPER, isSystemImageName: false){
                    print("go to dev")
                }
                
                ActionCardView(icon: "VersionIcon", label: Strings.TITLE_ABOUT_APP_VERSION + " " + version, isSystemImageName: false){
                    print("go to dev")
                    
                }
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
