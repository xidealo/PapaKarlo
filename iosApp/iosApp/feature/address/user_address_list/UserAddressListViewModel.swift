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
                
                var address = userAddress.street.name
                address += " д. " + (userAddress.house)
                
                if(userAddress.flat != nil && userAddress.flat != ""){
                    address += " кв. " + (userAddress.flat ?? "")
                }
                
                if(userAddress.entrance != nil && userAddress.entrance != ""){
                    address += " подъезд " + (userAddress.entrance ?? "")
                }
                
                if(userAddress.floor != nil && userAddress.floor != ""){
                    address += " этаж. " + (userAddress.floor ?? "")
                }
                
                if(userAddress.comment != nil && userAddress.comment != ""){
                    address += (userAddress.comment ?? "")
                }
                
                return AddressItem(id: userAddress.uuid, address: address)
                
            }
        }
        
    }
    
}
