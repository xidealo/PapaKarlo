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
    @State var showCopy:Bool = false
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    let paymentInfo = GetPaymentInfoUseCase().invoke()
    @State var paymentMethodList : [PaymentMethod]
    
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
                
                ForEach(paymentMethodList, id: \.self){ method in
                    if(method.valueToShow == nil){
                        ElementCardWithLocolized(text: method.name.getPaymentMethod())
                            .padding(.top, 8)
                    }else{
                        ActionCardView(
                            icon: "CopyIcon",
                            label: method.valueToShow ?? "",
                            isSystemImageName: false,
                            isShowRightArrow: false
                        ){
                            self.showCopy = true
                            UIPasteboard.general.string = method.valueToCopy ?? ""
                        }
                        .padding(.top, 8)
                    }
                }
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
                toast: Toast(title: "Значение скопировано"),
                show: $showCopy,
                backgroundColor:AppColor.primary,
                foregaroundColor: AppColor.onPrimary
            ), show: $showCopy
        )
    }
}

struct PaymentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentView(paymentMethodList:[])
    }
}
