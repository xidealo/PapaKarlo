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
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    init(){
        version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? "error"
    }
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleAboutApp",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                ActionCardView(icon: "DeveloperIcon", label: Strings.TITLE_ABOUT_APP_DEVELOPER, isSystemImageName: false, isShowRightArrow: true){
                    UIApplication.shared.open(
                        URL(string: "https://vk.com/bunbeauty")!
                    )
                }
                
                CardView(icon: "VersionIcon", label: Strings.TITLE_ABOUT_APP_VERSION + " " + version, isSystemImageName: false, isShowRightArrow: false
                ).padding(.top, Diems.SMALL_PADDING)
                
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(AppColor.background)
        .hiddenNavigationBarStyle()
    }
}

struct AboutAppView_Previews: PreviewProvider {
    static var previews: some View {
        AboutAppView()
    }
}
