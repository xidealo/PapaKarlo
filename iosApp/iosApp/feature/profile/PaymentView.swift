//
//  PaymentView.swift
//  iosApp
//
//  Created by Марк Шавловский on 16.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PaymentView: View {
    @State var showCardCopy:Bool = false
    @State var showPhoneCopy:Bool = false

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titlePayment",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                Text("Вы можете оплатить заказ наличным, а также переводом по номеру карты или по номеру телефона")
                    .multilineTextAlignment(.center)
                    .padding(.bottom, Diems.SMALL_PADDING)
                
                ActionCardView(icon: "CopyIcon", label: Strings.MSG_PAYMENT_PHONE, isSystemImageName: false, isShowRightArrow: false){
                    self.showCardCopy = false
                    self.showPhoneCopy = true
                    UIPasteboard.general.string = Strings.MSG_PAYMENT_PHONE
                }
                
                ActionCardView(
                    icon: "CopyIcon",
                    label: Strings.MSG_PAYMENT_CARD_NUMBER,
                    isSystemImageName: false,
                    isShowRightArrow: false
                ){
                    self.showPhoneCopy = false
                    self.showCardCopy = true
                    UIPasteboard.general.string = Strings.MSG_PAYMENT_CARD_NUMBER
                }
                .padding(.top, Diems.SMALL_PADDING)
            }.padding(Diems.MEDIUM_PADDING)
            Spacer()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Номер карты скопирован"),
                show: $showCardCopy,
                backgroundColor:Color("primary"),
                foregaroundColor: Color("onPrimary")
            ), show: $showCardCopy
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Номер телефона скопирован"),
                show: $showPhoneCopy,
                backgroundColor:Color("primary"),
                foregaroundColor: Color("onPrimary")
            ), show: $showPhoneCopy
        )
    }
}

struct PaymentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentView()
    }
}
