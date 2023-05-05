//
//  CafeListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI
import shared

struct CafeListView: View {
    
    @State var cafeViewState = CafeListState(
        cafeList: [],
        cartCostAndCount: nil,
        state: CafeListState.StateLoading(),
        eventList: []
    )
    
    var viewModel = CafeListViewModel(
        cafeInteractor: iosComponent.provideCafeInteractor(),
        getSelectedCityTimeZoneUseCase: iosComponent.provideGetSelectedCityTimeZoneUseCase(),
        getCafeListUseCase: iosComponent.provideGetCafeListUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase()
    )
    
    @State var listener: Closeable? = nil
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
            VStack(spacing:0){
                switch(cafeViewState.state){
                case is CafeListState.StateLoading : LoadingView()
                case is CafeListState.StateSuccess : SuccessCafeListView(
                    cafeList: cafeViewState.cafeList.map({ cafe in
                        CafeItemUi(
                            id: cafe.uuid,
                            address: cafe.address,
                            workingHours: cafe.workingHours,
                            cafeOpenState: cafe.cafeOpenState,
                            phone: cafe.phone,
                            latitude: 0.0,
                            longitude: 0.0
                        )
                    })
                )
                default:
                    EmptyView()
                }
                
            }
            .background(AppColor.background)
            .hiddenNavigationBarStyle()
            .onAppear(){
                viewModel.getCafeItemList()
                listener = viewModel.cafeListState.watch { cafeListStateVM in
                    if let cafeListStateVM = cafeListStateVM{
                        cafeViewState = cafeListStateVM
                        cafeViewState.eventList.forEach { event in
                            switch(event){
                            default:
                                print("def")
                            }
                        }
                        if !cafeListStateVM.eventList.isEmpty{
                            viewModel
                                .consumeEventList(eventList: cafeListStateVM.eventList)
                        }
                    }
                }
            }
            .onDisappear(){
                listener?.close()
                listener = nil
            }
    }
}


struct SuccessCafeListView: View {
    let cafeList : [CafeItemUi]
    
    var body: some View {
        ScrollView {
           LazyVStack(spacing:0){
               ForEach(cafeList){ cafe in
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
}


struct CafeListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeListView()
    }
}
