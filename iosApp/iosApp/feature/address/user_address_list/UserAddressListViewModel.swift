//
//  UserAddressListViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 11.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
class UserAddressListViewModel: ObservableObject {
    
    @Published var addressList:[AddressItem] = []
    
    
    init(){
        iosComponent.provideIAddressInteractor().observeAddressList().watch { list in
            self.addressList =  (list as! [UserAddress]).map { userAddress in
                AddressItem(id: userAddress.uuid, address: userAddress.street.name)
            }
        }
        
    }
    
}
