//
//  ProfileViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared


class ProfileViewModel: ToolbarViewModel {
    
    @Published var profileViewState:ProfileViewState = ProfileViewState(
        userUuid: "",
        hasAddresses: false,
        lastOrder: nil,
        isAuthorize: false,
        isLoading: true
    )

    override init() {
        super.init()
        print("ProfileViewModel")
    }
    
    func fetchProfile(){
        print("ProfileViewModel: fetch")

        iosComponent.provideIUserInteractor().getProfile { profile, error in
            if(profile is Profile.Authorized){
                let authorizedProfile = profile as! Profile.Authorized
                self.profileViewState =  ProfileViewState(userUuid: authorizedProfile.userUuid, hasAddresses: authorizedProfile.hasAddresses, lastOrder: OrderItem(id: authorizedProfile.lastOrder?.uuid ?? "", status: authorizedProfile.lastOrder?.status ?? OrderStatus.notAccepted, code: authorizedProfile.lastOrder?.code ?? "", dateTime: ""), isAuthorize: true, isLoading: false)
            }else{
                self.profileViewState =  ProfileViewState(userUuid: self.profileViewState.userUuid, hasAddresses: self.profileViewState.hasAddresses, lastOrder: nil, isAuthorize: false, isLoading: false)
            }
        }
    }
}
