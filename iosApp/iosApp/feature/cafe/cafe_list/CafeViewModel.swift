//
//  CafeViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation
import shared
import SwiftUI

class CafeViewModel: ObservableObject {
    
    @Published var cafeViewState:CafeViewState = CafeViewState(
        isLoading: false,
        cafeItemList: []
    )
    
    init(){
        print("CafeViewModel")
    }
    
    func fetchData(){
        print("CafeViewModel: fetch")
        
        (cafeViewState.copy() as! CafeViewState).apply { newState in
            newState.isLoading = true
            cafeViewState = newState
        }
        
        iosComponent.provideGetCafeListUseCase().invoke { cafeList, error in
            if(error != nil){
                print(error ?? "Nil error on cafeList")
            }
            
            self.cafeViewState = CafeViewState(isLoading: false, cafeItemList: cafeList?.map(
                { cafe in
                    let fromTime = iosComponent.provideCafeInteractor().getCafeTime(daySeconds: (cafe.fromTime))
                    let toTime = iosComponent.provideCafeInteractor().getCafeTime(daySeconds: (cafe.toTime))
                    
                    return CafeItem(
                        id: cafe.uuid,
                        address: cafe.address,
                        workingHours: fromTime + " - " + toTime,
                        isOpenMessage: "",
                        isOpenColor: Color.green,
                        phone: cafe.phone,
                        latitude: Float(cafe.latitude),
                        longitude: Float(cafe.longitude)
                    )
                }) ?? []
            )
        }
    }
    
}
