//
//  ProfileViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

class ProfileViewModel: ObservableObject {
    
    @Published var profileViewState:ProfileViewState = ProfileViewState(
        userUuid: "",
        hasAddresses: false,
        lastOrder: nil,
        profileState: ProfileState.loading,
        actionList: []
    )
    
    let dateUtil = DateUtil()

    init() {
        print("ProfileViewModel")
    }
    
    func fetchProfile(){
        print("ProfileViewModel: fetch")
        
        (profileViewState.copy() as! ProfileViewState).apply { newState in
            newState.profieState = ProfileState.loading
            profileViewState = newState
        }
        
        iosComponent.provideIUserInteractor().getProfile { profile, error in
            if(profile is Profile.Authorized){
                let authorizedProfile = profile as! Profile.Authorized
                var dateTime = ""
                if(authorizedProfile.lastOrder != nil){
                    dateTime = self.dateUtil.getDateTimeString(dateTime: authorizedProfile.lastOrder!.dateTime)
                }
                DispatchQueue.main.async {
                    (self.profileViewState.copy() as! ProfileViewState).apply { newState in
                        newState.userUuid = authorizedProfile.userUuid
                        newState.hasAddresses = authorizedProfile.hasAddresses
                        
                        if(authorizedProfile.lastOrder != nil){
                            newState.lastOrder =
                            OrderItem(id: authorizedProfile.lastOrder?.uuid ?? "", status: authorizedProfile.lastOrder?.status ?? OrderStatus.notAccepted, code: authorizedProfile.lastOrder?.code ?? "", dateTime: dateTime)
                        }
                        
                        newState.profieState = ProfileState.success
                        
                        self.profileViewState = newState
                    }
                }
            }else{
                self.profileViewState =  ProfileViewState(userUuid: self.profileViewState.userUuid, hasAddresses: self.profileViewState.hasAddresses, lastOrder: nil, profileState: ProfileState.notAuthorize, actionList: self.profileViewState.actionList)
            }
        }
    }
    
    func subscribeOnOrders(){
        iosComponent.provideMainInteractor().startCheckOrderUpdates { err in
            if(err != nil){
                print(err ?? "")
            }
        }
    }
    
    func unsubscribeFromOrders(){
        iosComponent.provideMainInteractor().stopCheckOrderUpdates { err in
            if(err != nil){
                print(err ?? "")
            }
        }
    }
    
    func observeLastOrder(){
        iosComponent.provideIUserInteractor().observeIsUserAuthorize().watch { isAuthorize in
            if(isAuthorize == true){
                iosComponent.provideIOrderInteractor().observeLastOrder().watch { lightOrder in
                    if(lightOrder != nil){
                        (self.profileViewState.copy() as! ProfileViewState).apply { newState in
                            newState.lastOrder = OrderItem(
                                id: lightOrder?.uuid ?? "",
                                status: lightOrder?.status ?? OrderStatus.notAccepted,
                                code: lightOrder?.code ?? "",
                                dateTime: self.dateUtil.getDateTimeString(dateTime: lightOrder!.dateTime)

                            )
                            self.profileViewState = newState
                        }
                    }
                }
            }
        }
    }
    
    func clearActions(){
        (self.profileViewState.copy() as! ProfileViewState).apply { copiedState in
            copiedState.actionList = []
            self.profileViewState = copiedState
        }
    }
    
}

enum ProfileAction {
   case goToLogin
}
