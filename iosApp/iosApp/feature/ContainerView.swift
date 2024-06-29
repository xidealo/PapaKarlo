//
//  ContainerView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct ContainerView: View {
    
    @State var selection: MainContainerState
    @State var title: LocalizedStringKey = "title_menu"
    
    @StateObject private var viewModel = ToolbarViewModel()
    
    @State var showOrderCreated: Bool = false
    @State var showCreatedAddress: Bool = false

    @State var goToCreateOrder = false
    @State var isShowLogo: Bool = true
    
    var body: some View {
        VStack(spacing:0){
            ToolbarWithCartView(
                title: title,
                cost: viewModel.toolbarViewState.cost,
                count: viewModel.toolbarViewState.count,
                isShowLogo: $isShowLogo,
                isRootActive: $goToCreateOrder,
                selection: $selection,
                showOrderCreated: $showOrderCreated
            )
            switch(selection){
                case .cafeList : CafeListView()
                case .menu: MenuView(
                    isRootActive: $showOrderCreated,
                    selection: $selection,
                    showOrderCreated: $showOrderCreated
                )
                case .profile : ProfileView(showOrderCreated: $showOrderCreated, showCreatedAddress: $showCreatedAddress)
            }
            BottomBarView(selection: $selection, title: $title)
        }
        .onAppear(){
            viewModel.subscribeOnFlow()
        }
        .onDisappear(){
            viewModel.unsubFromFlows()
        }.onChange(of: selection) { newValue in
            isShowLogo = newValue == .menu
            
            switch(newValue){
                case .cafeList: title = "title_restaurants"
                case .menu: title = "title_menu"
                case .profile: title = "title_profile"
            }
        }
    }
}

enum MainContainerState{
    case cafeList
    case menu
    case profile
}
