//
//  CafeListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

struct CafeListView: View {
        
    @ObservedObject private var viewModel = CafeViewModel()

    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_CAFE_LIST, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count,  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            if viewModel.cafeViewState.isLoading{
                LoadingView()
            }
            else{
                ScrollView {
                    LazyVStack{
                        ForEach(viewModel.cafeViewState.cafeItemList){ cafe in
                            NavigationLink(
                                destination: CafeOptionsView(phone: cafe.phone, address: cafe.address, latitude: cafe.latitude, longitude: cafe.longitude)
                            ){
                                CafeItemView(cafeItem: cafe)
                                    .padding(.bottom, Diems.SMALL_PADDING)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                            }
                        }
                    }
                }.padding(.top, Diems.MEDIUM_PADDING)
            }
        }.background(Color("background"))
    }
}

struct CafeListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeListView()
    }
}
