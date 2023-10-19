//
//  SelectablePaymentListView.swift
//  iosApp
//
//  Created by Марк Шавловский on 22.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct SelectablePaymentListView: View {
    
    //@ObservedObject private var viewModel : CafeAddressViewModel
    var title: LocalizedStringKey = "selectable_payment_method"
    var paymentList: [SelectablePaymentMethod]
    @Binding var selectedPaymentUuid:String?
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: title,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            SuccessSelectablePaymentListView(
                paymentList: paymentList.map({ selectablePayment in
                PaymentItem(id: selectablePayment.paymentMethod.uuid, selectablePayment: selectablePayment)
            }),
                selectedPaymentUuid : $selectedPaymentUuid)
        }.hiddenNavigationBarStyle()
            .background(AppColor.background)
    }
}

//
  //
//            .onReceive(viewModel.$cafeAddressViewState, perform: { cafeAddressViewState in
//                if(cafeAddressViewState.cafeAddressState == CafeAddressState.goBack){
//                    self.mode.wrappedValue.dismiss()
//                }
//            })

struct SuccessSelectablePaymentListView: View {
    var paymentList: [PaymentItem]
    @Binding var selectedPaymentUuid:String?
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing:0){
            ScrollView {
                LazyVStack(spacing:0){
                    ForEach(paymentList){ payment in
                        Button(action: {
                            selectedPaymentUuid = payment.id
                            self.mode.wrappedValue.dismiss()
                        }) {
                            SelectableElementCard(
                                locolized:payment.selectablePayment.paymentMethod.name.getPaymentMethod(),
                                isSelected:payment.selectablePayment.isSelected
                            )
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.SMALL_PADDING)
                        }
                    }
                }.padding(.top, Diems.SMALL_PADDING)
            }
        }
    }
}

//payment.paymentMethod.name.getPaymentMethod()
struct PaymentItem:Identifiable {
    var id : String
    var selectablePayment:SelectablePaymentMethod
}
