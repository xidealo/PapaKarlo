//
//  CafeListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import shared
import SwiftUI

struct CafeListView: View {
    @State var cafeViewState = CafeListViewState(state: .loading)

    var viewModel = CafeListViewModel(
        cafeInteractor: iosComponent.provideCafeInteractor(),
        observeCafeWithOpenStateListUseCase: iosComponent.provideObserveCafeWithOpenStateListUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase()
    )

    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing: 0) {
            switch cafeViewState.state {
            case let .success(cafeItemList, costAndCount):
                SuccessCafeListView(
                    cafeList: cafeItemList
                )
            case .loading: LoadingView()
            case .error: ErrorView(
                    mainText: "Что-то пошло не так",
                    extratext: ""
                ) {}
            }
        }
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
        .onAppear {
            subscribe()
        }
        .onDisappear {
            listener?.close()
            listener = nil
        }
    }

    func subscribe() {
        viewModel.onAction(action: CafeListActionInit())
        listener = viewModel.dataState.watch { cafeListStateVM in
            if let cafeListStateVM = cafeListStateVM {
                if cafeListStateVM.throwable != nil {
                    cafeViewState = CafeListViewState(state: CafeListCartState.error)
                    return
                }

                if cafeListStateVM.isLoading {
                    cafeViewState = CafeListViewState(state: CafeListCartState.loading)
                    return
                }
                cafeViewState = CafeListViewState(
                    state: CafeListCartState.success(
                        cafeListStateVM.cafeList.map(
                            { cafe in
                                CafeItem(
                                    id: cafe.uuid,
                                    address: cafe.address,
                                    phone: cafe.phone,
                                    workingHours: cafe.workingHours,
                                    cafeOpenState: cafe.cafeOpenState,
                                    latitude: 0.0,
                                    longitude: 0.0
                                )
                            }
                        ),
                        CartCostAndCount(
                            cost: cafeListStateVM.cartCostAndCount?.cost ?? "",
                            count: cafeListStateVM.cartCostAndCount?.count ?? ""
                        )
                    )
                )
            }
        }
    }
}

struct SuccessCafeListView: View {
    let cafeList: [CafeItem]

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(cafeList) { cafe in
                    NavigationLink(
                        destination: CafeOptionsView(phone: cafe.phone, address: cafe.address, latitude: cafe.latitude, longitude: cafe.longitude)
                    ) {
                        CafeItemView(cafeItem: cafe)
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
