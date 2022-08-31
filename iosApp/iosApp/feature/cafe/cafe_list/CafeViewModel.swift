//
//  CafeViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared
import SwiftUI

class CafeViewModel: ToolbarViewModel {
    
    @Published var cafeViewState:CafeViewState = CafeViewState(isLoading: false, cafeItemList: [])
    
    override init(){
        super.init()
        
        print("CafeViewModel")
    }
    
    func fetchData(){
        print("CafeViewModel: fetch")

        (cafeViewState.copy() as! CafeViewState).apply { newState in
            newState.isLoading = true
            cafeViewState = newState
        }
        
        iosComponent.provideCafeInteractor().getCafeList { cafeList, error in
            self.cafeViewState = CafeViewState(isLoading: false, cafeItemList: cafeList?.map({ cafe in
                let fromTime = iosComponent.provideCafeInteractor().getCafeTime(daySeconds: (cafe.fromTime))
                let toTime = iosComponent.provideCafeInteractor().getCafeTime(daySeconds: (cafe.toTime))
                
                return CafeItem(id: cafe.uuid, address: cafe.address, workingHours: fromTime + Constants.init().WORKING_HOURS_DIVIDER + toTime, isOpenMessage: "Open", isOpenColor: Color.green, phone: cafe.phone, latitude: Float(cafe.latitude), longitude: Float(cafe.longitude))
            }) ?? [])
        }
    }
    
}
