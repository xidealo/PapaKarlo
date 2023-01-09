//
//  ToolbarViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.07.2022.
//

import Foundation
import shared

class ToolbarViewModel : ObservableObject {
    @Published var toolbarViewState:ToolbarViewState = ToolbarViewState(count: "", cost: "")
    
    var subOnTotalCartCount : Closeable? = nil
    var subOnTotalCartCost : Closeable? = nil

    func subscribeOnFlow(){
        subOnTotalCartCount = iosComponent.provideCartProductInteractor().observeTotalCartCount().watch { count in
            self.toolbarViewState = ToolbarViewState(count: String(count as? Int ?? 0), cost: self.toolbarViewState.cost)
        }
        subOnTotalCartCost = iosComponent.provideCartProductInteractor().observeNewTotalCartCost().watch { cost in
            self.toolbarViewState = ToolbarViewState(count: self.toolbarViewState.count, cost: String(cost as? Int ?? 0))
        }
    }
    
    func unsubFromFlows(){
        subOnTotalCartCost?.close()
        subOnTotalCartCost = nil
        subOnTotalCartCount?.close()
        subOnTotalCartCount = nil
    }
}
