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
    var title: LocalizedStringKey = "selectable_payment_method"
    var paymentList: [SelectablePaymentMethodUI]
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    var closedCallback: (String?) -> Void

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: title,
                back: {
                    closedCallback(nil)
                    self.mode.wrappedValue.dismiss()
                }
            )
            SuccessSelectablePaymentListView(
                paymentList: paymentList,
                closedCallback: closedCallback
            )
        }.hiddenNavigationBarStyle()
            .background(AppColor.background)
    }
}

struct SuccessSelectablePaymentListView: View {
    var paymentList: [SelectablePaymentMethodUI]
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var closedCallback: (String?) -> Void

    var body: some View {
        VStack(spacing: 0) {
            ScrollView {
                LazyVStack(spacing: 0) {
                    ForEach(paymentList) { payment in
                        Button(action: {
                            closedCallback(payment.id)
                            self.mode.wrappedValue.dismiss()
                        }) {
                            SelectableElementCard(
                                locolized: payment.name,
                                isSelected: payment.isSelected,
                                isEnabled: true
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
