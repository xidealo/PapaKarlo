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
    @ObservedObject private var viewModel = ToolbarViewModel()
    @State var showOrderCreated:Bool = false
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: title,
                cost: viewModel.toolbarViewState.cost,
                count: viewModel.toolbarViewState.count,
                isCartVisible: true
            )
            switch(selection){
                case 0 : CafeListView()
                case 1: MenuView()
                default : ProfileView(showOrderCreated: showOrderCreated)
            }
            BottomBarView(selection: $selection, title: $title)
        }
    }
}
