//
//  CafeAddressViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
class CafeAddressViewModel: ObservableObject {
    
    @Published var cafeAddressViewState:CafeAddressViewState = CafeAddressViewState(cafeAddressState: CafeAddressState.loading, addressItemList: [])
    
    init(isClickable:Bool){
        loadData(isClickable: isClickable)
    }
    
    func loadData(isClickable:Bool) {
        iosComponent.provideCafeInteractor().observeCafeAddressList().watch { list in
            self.cafeAddressViewState = CafeAddressViewState(cafeAddressState: CafeAddressState.success, addressItemList: (list as! [CafeAddress]).map({ cafeAddress in
                AddressItem(
                    id: cafeAddress.cafeUuid,
                    address: cafeAddress.address,
                    isClickable: isClickable,
                    isSelected: false
                )
            }))
        }
    }
    
    func selectAddress(uuid:String){
        iosComponent.provideCafeInteractor().saveSelectedCafe(cafeUuid: uuid) { err in
            (self.cafeAddressViewState.copy() as! CafeAddressViewState).apply { copiedState in
                copiedState.cafeAddressState = CafeAddressState.goBack
                self.cafeAddressViewState = copiedState
            }
        }
    }
    
}


enum CafeAddressState {
    case loading, empty, success, goBack
}
