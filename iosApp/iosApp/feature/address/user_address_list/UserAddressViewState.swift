//
//  UserAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class UserAddressViewState : NSObject, NSCopying {
    
    var userAddressState:UserAddressState
    var addressItemList:[AddressItem]
    
    init(
        userAddressState:UserAddressState,
        addressItemList:[AddressItem]
    ){
        self.userAddressState = userAddressState
        self.addressItemList = addressItemList
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = UserAddressViewState(
            userAddressState: userAddressState,
            addressItemList: addressItemList
        )
        return copy
    }
}
