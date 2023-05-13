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
    let socialNetworkInfo = GetSocialNetworkLinksUseCase().invoke()

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleFeedback",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                if let vkLink = socialNetworkInfo.vkLink{
                    ActionCardView(icon: "VKIcon", label: Strings.TITLE_FEEDBACK_VK, isSystemImageName: false, isShowRightArrow: true){
                        UIApplication.shared.open(
                            URL(string: vkLink)!
                        )
                    }
                }
                if let instagramLink = socialNetworkInfo.instagramLink{
                    ActionCardView(icon: "InstagramIcon", label: Strings.TITLE_FEEDBACK_INSTAGRAM, isSystemImageName: false, isShowRightArrow: true){
                        UIApplication.shared.open(
                            URL(string: instagramLink)!
                        )
                    }
                    .padding(.top, Diems.SMALL_PADDING)
                }
                
                if let appStoreLink = socialNetworkInfo.appStoreLink{
                    ActionCardView(icon: "AppleIcon", label: Strings.TITLE_FEEDBACK_APP_STORE, isSystemImageName: false, isShowRightArrow: true){
                        UIApplication.shared.open(
                            URL(string: appStoreLink)!
                        )
                    }
                    .padding(.top, Diems.SMALL_PADDING)
                }


            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
    }
}

struct FeedbackView_Previews: PreviewProvider {
    static var previews: some View {
        FeedbackView()
    }
}
