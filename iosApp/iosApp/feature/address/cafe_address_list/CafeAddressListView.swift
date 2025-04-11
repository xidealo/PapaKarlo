//
//  CafeAddressListView.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import SwiftUI

struct CafeAddressListView: View {
    @ObservedObject private var viewModel: CafeAddressViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    var title: LocalizedStringKey = "titleCafeAddresses"

    var closedCallback: () -> Void

    init(
        isClickable _: Bool,
        _title: LocalizedStringKey = "titleCafeAddresses",
        addressList: [SelectableAddressUI],
        _closedCallback: @escaping () -> Void
    ) {
        title = _title
        viewModel = CafeAddressViewModel(
            isClickable: true,
            addressList: addressList
        )
        closedCallback = _closedCallback
    }

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: title,
                back: {
                    back()
                }
            )
            switch viewModel.cafeAddressViewState.cafeAddressState {
            case CafeAddressState.loading: LoadingView()
            default: SuccessCafeAddressListView(viewModel: viewModel)
            }
        }.hiddenNavigationBarStyle()
            .background(AppColor.background)
            .onReceive(
                viewModel.$cafeAddressViewState,
                perform: { cafeAddressViewState in
                    if cafeAddressViewState.cafeAddressState == CafeAddressState.goBack {
                        back()
                    }
                }
            )
    }

    func back() {
        closedCallback()
        mode.wrappedValue.dismiss()
    }
}

struct SuccessCafeAddressListView: View {
    let viewModel: CafeAddressViewModel

    var body: some View {
        VStack(spacing: 0) {
            ScrollView {
                LazyVStack(spacing: 0) {
                    ForEach(viewModel.cafeAddressViewState.addressItemList) { address in
                        if address.isClickable && address.isEnabled {
                            Button(action: {
                                // save selected
                                viewModel.selectAddress(uuid: address.id)
                            }) {
                                AddressItemView(addressItem: address)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                            }
                        } else {
                            AddressItemView(addressItem: address)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.top, Diems.SMALL_PADDING)
                        }
                    }
                }.padding(.top, Diems.SMALL_PADDING)
            }
        }
    }
}
