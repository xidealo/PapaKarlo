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
    
    init(){
        iosComponent.provideCartProductInteractor().observeTotalCartCountForIos().watch { count in
            self.toolbarViewState = ToolbarViewState(count: String(count as? Int ?? 0), cost: self.toolbarViewState.cost)
        }
        iosComponent.provideCartProductInteractor().observeNewTotalCartCostForIos().watch { cost in
            self.toolbarViewState = ToolbarViewState(count: self.toolbarViewState.count, cost: String(cost as? Int ?? 0))
        }
    }
}
