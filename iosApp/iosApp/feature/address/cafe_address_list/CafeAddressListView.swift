//
//  CafeAddressListView.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CafeAddressListView: View {
    
    @ObservedObject private var viewModel = CafeAddressViewModel()
    
    let isClickable:Bool
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_MY_ADDRESSES, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            SuccessCafeAddressListView(addressList: viewModel.addressList)
                .onAppear {
                    viewModel.loadData(isClickable: isClickable)
            }
        }.hiddenNavigationBarStyle()
            .background(Color("background"))
    }
}

struct CafeAddressListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeAddressListView(isClickable: false)
    }
}


struct SuccessCafeAddressListView: View {
    let addressList: [AddressItem]

    var body: some View {
        VStack(spacing:0){
            ScrollView {
                LazyVStack{
                    ForEach(addressList){ address in
                        if(address.isClickable){
                            Button(action: {
                                

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
                }
            }
        }
       
    }
}
