//
//  OrderListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import shared
import SwiftUI

struct OrderListView: View {
    @State var orderListState = OrderListStateDataState(
        orderList: [],
        state: OrderListStateDataState.State.loading
    )
    
    var viewModel = OrderListViewModel(
        observeOrderListUseCase: iosComponent.provideObserveOrderListUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase()
    )

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var listener: Closeable?

    @Environment(\.scenePhase) var scenePhase

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: "titleMyOrders",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            switch orderListState.state {
            case OrderListStateDataState.State.loading: LoadingView()
            case OrderListStateDataState.State.empty: EmptyOrderListView()
            case OrderListStateDataState.State.success: SuccessOrderListView(
                    orderList: orderListState.orderList.map { lightOrder in
                        OrderItem(
                            id: lightOrder.uuid,
                            status: lightOrder.status,
                            code: lightOrder.code,
                            dateTime: dateUtil.getDateTimeString(dateTime: lightOrder.dateTime)
                        )
                    }
                )
            default:
                EmptyView()
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear {
            subscribe()
        }
        .onDisappear {
            unsubscribe()
        }
        .onChange(of: scenePhase) { newPhase in
            if newPhase == .active {
                subscribe()
            } else if newPhase == .inactive {
                unsubscribe()
            } else if newPhase == .background {
                unsubscribe()
            }
        }
    }

    func subscribe() {
        viewModel.onAction(action: OrderListStateActionStartObserveOrder())

        listener = viewModel.dataState.watch { orderListVM in
            if let notNullorderListVM = orderListVM {
                orderListState = notNullorderListVM
            }
        }
    }

    func unsubscribe() {
        viewModel.onAction(action: OrderListStateActionStopObserveOrder(), )
        listener?.close()
        listener = nil
    }
}

struct OrderListView_Previews: PreviewProvider {
    static var previews: some View {
        OrderListView()
    }
}

struct SuccessOrderListView: View {
    let orderList: [OrderItem]

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(orderList) { order in
                    OrderItemView(
                        orderItem: order,
                        destination: OrderDetailsView(orderUuid: order.id)
                    )
                    .padding(.bottom, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
        }
    }
}

struct EmptyOrderListView: View {
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            EmptyWithIconView(
                imageName: "ic_history",
                title: "emptyOrderListTitleProfile",
                secondText: "emptyOrderListSecondProfile"
            )
            Spacer()
        }
    }
}
