//
//  FeedbackView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct FeedbackView: View {
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_FEEDBACK, cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "VKIcon", label: Strings.TITLE_FEEDBACK_VK, isSystemImageName: false){
                    print("go to dev")
                }
                
                ActionCardView(icon: "InstagramIcon", label: Strings.TITLE_FEEDBACK_INSTAGRAM, isSystemImageName: false){
                    print("go to dev")
                }
                
                ActionCardView(icon: "PlayMarketIcon", label: Strings.TITLE_FEEDBACK_PLAYMARKET, isSystemImageName: false){
                    print("go to dev")
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
