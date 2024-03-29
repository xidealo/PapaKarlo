//
//  CafeAddressListView.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CafeAddressListView: View {
    
    @ObservedObject private var viewModel : CafeAddressViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    var title: LocalizedStringKey = "titleCafeAddresses"

    init(
        isClickable:Bool,
        _title : LocalizedStringKey = "titleCafeAddresses",
        addressList: [SelectableCafeAddressItem]
    ){
        title = _title
        viewModel = CafeAddressViewModel(
            isClickable: true,
            addressList:addressList
        )
    }
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: title,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            switch(viewModel.cafeAddressViewState.cafeAddressState){
            case CafeAddressState.loading : LoadingView()
            default : SuccessCafeAddressListView(viewModel: viewModel)
            }
        }.hiddenNavigationBarStyle()
            .background(AppColor.background)
            .onReceive(viewModel.$cafeAddressViewState, perform: { cafeAddressViewState in
                if(cafeAddressViewState.cafeAddressState == CafeAddressState.goBack){
                    self.mode.wrappedValue.dismiss()
                }
            })
    }
}

struct CafeAddressListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeAddressListView(isClickable: false, addressList: [])
    }
}


struct SuccessCafeAddressListView: View {
    let viewModel: CafeAddressViewModel

    var body: some View {
        VStack(spacing:0){
            ScrollView {
                LazyVStack(spacing:0){
                    ForEach(viewModel.cafeAddressViewState.addressItemList){ address in
                        if(address.isClickable){
                            Button(action: {
                                //save selected
                                viewModel.selectAddress(uuid: address.id)
                            }) {
                                AddressItemView(addressItem: address)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                            }
                        }else{
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
