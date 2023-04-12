//
//  CafeAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 25.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class CafeAddressViewState : NSObject, NSCopying {
    
    var cafeAddressState:CafeAddressState
    var addressItemList:[AddressItem]
    
    init(
        cafeAddressState:CafeAddressState,
        addressItemList:[AddressItem]
    ){
        self.cafeAddressState = cafeAddressState
        self.addressItemList = addressItemList
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = CafeAddressViewState(
            cafeAddressState: cafeAddressState,
            addressItemList: addressItemList
        )
        return copy
    }
}

