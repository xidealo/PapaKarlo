//
//  PaymentView.swift
//  iosApp
//
//  Created by Марк Шавловский on 16.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PaymentView: View {
    @State var showCardCopy:Bool = false
    @State var showPhoneCopy:Bool = false
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    let paymentInfo = GetPaymentInfoUseCase().invoke()
    
    @State var payment:Payment? = nil
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titlePayment",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                Text(paymentInfo)
                    .multilineTextAlignment(.center)
                    .padding(.bottom, Diems.SMALL_PADDING)
                
                if(payment == nil){
                    LoadingView()
                }else{
                    if let phone = payment?.phoneNumber{
                        ActionCardView(
                            icon: "CopyIcon",
                            label: phone,
                            isSystemImageName: false,
                            isShowRightArrow: false
                        ){
                            self.showCardCopy = false
                            self.showPhoneCopy = true
                            UIPasteboard.general.string = Strings.MSG_PAYMENT_PHONE
                        }
                    }
                    if let card = payment?.cardNumber{
                        ActionCardView(
                            icon: "CopyIcon",
                            label: card,
                            isSystemImageName: false,
                            isShowRightArrow: false
                        ){
                            self.showPhoneCopy = false
                            self.showCardCopy = true
                            UIPasteboard.general.string = Strings.MSG_PAYMENT_CARD_NUMBER
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                    }
                }
            }.padding(Diems.MEDIUM_PADDING)
            Spacer()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
//            paymentInteractor.getPayment { _payment, err in
//                payment = _payment
//            }
        }
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
