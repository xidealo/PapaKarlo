//
//  FeedbackView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared

struct FeedbackView: View {
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_FEEDBACK, cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "VKIcon", label: Strings.TITLE_FEEDBACK_VK, isSystemImageName: false){
                    UIApplication.shared.open(URL(string: Constants.init().VK_LINK)!)
                }
                
                ActionCardView(icon: "InstagramIcon", label: Strings.TITLE_FEEDBACK_INSTAGRAM, isSystemImageName: false){
                    UIApplication.shared.open(URL(string: Constants.init().INSTAGRAM_LINK)!)
                }
                
                ActionCardView(icon: "PlayMarketIcon", label: Strings.TITLE_FEEDBACK_APP_STORE, isSystemImageName: false){
                    UIApplication.shared.open(URL(string: Constants.init().PLAY_MARKET_LINK)!)
                }
            }.padding(Diems.MEDIUM_PADDING)
          
            Spacer()
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)
    }
}

struct FeedbackView_Previews: PreviewProvider {
    static var previews: some View {
        FeedbackView()
    }
}
