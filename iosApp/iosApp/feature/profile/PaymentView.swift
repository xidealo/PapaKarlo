//
//  PaymentView.swift
//  iosApp
//
//  Created by Марк Шавловский on 16.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PaymentView: View {
    @State var show:Bool = false
    
    @State var toastText:String = ""

    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_PAYMENT, cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            VStack{
                ActionCardView(icon: "CopyIcon", label: Strings.MSG_PAYMENT_PHONE, isSystemImageName: false, isShowRightArrow: false){
                    self.show = true
                    self.toastText = "Номер телефона скопирован"
                    UIPasteboard.general.string = Strings.MSG_PAYMENT_PHONE
                }
                
                ActionCardView(icon: "CopyIcon", label: Strings.MSG_PAYMENT_CARD_NUMBER, isSystemImageName: false, isShowRightArrow: false){
                    self.show = true
                    self.toastText = "Номер карты скопирован"
                    UIPasteboard.general.string = Strings.MSG_PAYMENT_CARD_NUMBER
                }
            }.padding(Diems.MEDIUM_PADDING)
            Spacer()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)
        .overlay(overlayView: ToastView(toast: Toast(title: toastText), show: $show, backgroundColor:Color("primary"), foregaroundColor: Color("onPrimary")), show: $show)
    }
}

struct PaymentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentView()
    }
}
