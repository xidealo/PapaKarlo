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
    var addressItemist:[AddressItem]
    
    init(
        userAddressState:UserAddressState,
        addressItemist:[AddressItem]
    ){
        self.userAddressState = userAddressState
        self.addressItemist = addressItemist
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = UserAddressViewState(
            userAddressState: userAddressState,
            addressItemist: addressItemist
        )
        return copy
    }
}
