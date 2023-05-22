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
   // let socialNetworkInfo = GetSocialNetworkLinksUseCase().invoke()
    var linkList: [shared.Link]
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleFeedback",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                
                ForEach(Array(linkList.enumerated()), id: \.offset){ index, link in
                    
                    switch(link.type){
                    case LinkType.appStore:
                        ActionCardView(
                            icon: "AppleIcon",
                            label: Strings.TITLE_FEEDBACK_APP_STORE,
                            isSystemImageName: false,
                            isShowRightArrow: true){
                                UIApplication.shared.open(
                                    URL(string: link.linkValue)!
                                )
                        }
                        .padding(.top, index == 0 ? 0 : 8)
                    case LinkType.vkontakte:
                        ActionCardView(
                            icon: "VKIcon",
                            label: Strings.TITLE_FEEDBACK_VK,
                            isSystemImageName: false,
                            isShowRightArrow: true
                        ){
                            UIApplication.shared.open(
                                URL(string: link.linkValue)!
                            )
                        }
                        .padding(.top, index == 0 ? 0 : 8)

                    case LinkType.instagram:  ActionCardView(
                        icon: "InstagramIcon",
                        label: Strings.TITLE_FEEDBACK_INSTAGRAM,
                        isSystemImageName: false,
                        isShowRightArrow: true
                    ){
                        UIApplication.shared.open(
                            URL(string: link.linkValue)!
                        )
                    }
                    .padding(.top, index == 0 ? 0 : 8)

                    case LinkType.instagram:  ActionCardView(
                        icon: "InstagramIcon",
                        label: Strings.TITLE_FEEDBACK_INSTAGRAM,
                        isSystemImageName: false,
                        isShowRightArrow: true
                    ){
                        UIApplication.shared.open(
                            URL(string: link.linkValue)!
                        )
                    }
                    .padding(.top, index == 0 ? 0 : 8)

                    default : EmptyView()
                    }
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
        FeedbackView(linkList: [])
    }
}
