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
    
    @Published var addressList:[AddressItem] = []
    
    func loadData() {
        iosComponent.provideCafeInteractor().observeCafeAddressList().watch { list in
            self.addressList = (list as! [CafeAddress]).map({ cafeAddress in
                AddressItem(id: cafeAddress.cafeUuid, address: cafeAddress.address)
            })
        }
    }
    
}


