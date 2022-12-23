//
//  CafeListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

struct CafeListView: View {
    
    @StateObject private var viewModel = CafeViewModel()
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
            VStack(spacing:0){
                if viewModel.cafeViewState.isLoading{
                    LoadingView()
                }
                else{
                    ScrollView {
                        LazyVStack(spacing:0){
                            ForEach(viewModel.cafeViewState.cafeItemList){ cafe in
                                NavigationLink(
                                    destination: CafeOptionsView(phone: cafe.phone, address: cafe.address, latitude: cafe.latitude, longitude: cafe.longitude)
                                ){
                                    CafeItemView(cafeItem: cafe)
                                        .padding(.bottom, Diems.SMALL_PADDING)
                                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                                }
                            }
                        }.padding(.top, Diems.MEDIUM_PADDING)
                    }
                }
            }.background(Color("background")).hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.fetchData()
        }
    }
}

struct CafeListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeListView()
    }
}
