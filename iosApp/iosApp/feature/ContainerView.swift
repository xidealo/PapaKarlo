//
//  ContainerView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct ContainerView: View {
    
    @State var selection:Int
    @State var title:String = Strings.TITLE_MENU
    @StateObject private var viewModel = ToolbarViewModel()
    @State var showOrderCreated:Bool = false
    @State var showCreatedAddress:Bool = false

    @State var goToCreateOrder = false
    
    var body: some View {
        VStack(spacing:0){
            ToolbarWithCartView(
                title: title,
                cost: viewModel.toolbarViewState.cost,
                count: viewModel.toolbarViewState.count,
                isRootActive: $goToCreateOrder,
                selection: $selection,
                showOrderCreated: $showOrderCreated
            )
            switch(selection){
                case 0 : CafeListView()
            case 1: MenuView(isRootActive: $showOrderCreated, selection: $selection, showOrderCreated: $showOrderCreated)
                default : ProfileView(showOrderCreated: $showOrderCreated, showCreatedAddress: $showCreatedAddress)
            }
            BottomBarView(selection: $selection, title: $title)
        }
    }
}
