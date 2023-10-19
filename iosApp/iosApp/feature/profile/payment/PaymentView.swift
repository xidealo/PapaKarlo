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
    let placeHolder:String? = nil

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
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
                Text("available_payments")
                    .bodyMedium()
                    .multilineTextAlignment(.leading)
                    .foregroundColor(AppColor.onSurface)
                    .frame(maxWidth: .infinity, alignment: .leading)
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
                            isShowRightArrow: false,
                            placeHolder: method.name.getPaymentMethod()
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
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Номер карты скопирован"),
                show: $showCardCopy,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ), show: $showCardCopy
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Значение скопировано"),
                show: $showCopy,
                backgroundColor:AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ), show: $showCopy
        )
    }
}

struct PaymentView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentView(paymentMethodList:[])
    }
}
