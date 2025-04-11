//
//  ToolbarViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.07.2022.
//

import Foundation
import shared

class ToolbarViewModel: ObservableObject {
    @Published var toolbarViewState: ToolbarViewState = .init(count: "", cost: "")

    var closable: Closeable? = nil

    func subscribeOnFlow() {
        closable = iosComponent.provideObserveCartUseCase().invoke().watch { cartCostAndCount in
            self.toolbarViewState = ToolbarViewState(count: cartCostAndCount?.count ?? "", cost: cartCostAndCount?.cost ?? "")
        }
    }

    func unsubFromFlows() {
        closable?.close()
        closable = nil
    }
}
