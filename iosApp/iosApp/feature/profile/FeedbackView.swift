//
//  FeedbackView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared

struct FeedbackView: View {
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: Strings.TITLE_FEEDBACK,
                cost: "",
                count: "2",
                isCartVisible: false,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                ActionCardView(icon: "VKIcon", label: Strings.TITLE_FEEDBACK_VK, isSystemImageName: false, isShowRightArrow: true){
                    UIApplication.shared.open(URL(string: Constants.init().VK_LINK)!)
                }
                
                ActionCardView(icon: "InstagramIcon", label: Strings.TITLE_FEEDBACK_INSTAGRAM, isSystemImageName: false, isShowRightArrow: true){
                    UIApplication.shared.open(URL(string: Constants.init().INSTAGRAM_LINK)!)
                }
                .padding(.top, Diems.SMALL_PADDING)
                
                ActionCardView(icon: "AppleIcon", label: Strings.TITLE_FEEDBACK_APP_STORE, isSystemImageName: false, isShowRightArrow: true){
                    UIApplication.shared.open(URL(string: Constants.init().APP_STORE_LINK)!)
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

struct FeedbackView_Previews: PreviewProvider {
    static var previews: some View {
        FeedbackView()
    }
}
