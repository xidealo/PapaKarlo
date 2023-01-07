//
//  OrderListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI
import shared

struct OrderListView: View {
    
    @State var orderListState = OrderListState(
        orderList: [],
        eventList: [],
        state: OrderListState.State.loading
    )
    
    var viewModel = OrderListViewModel(
        observeOrderListUseCase: iosComponent.provideObserveOrderListUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase()
    )
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing: 0 ){
            ToolbarView(
                title: Strings.TITLE_MY_ORDERS,
                back: {
                    self.mode.wrappedValue.dismiss()
                })
            
            switch(orderListState.state){
            case OrderListState.State.loading : LoadingView()
            case OrderListState.State.empty : EmptyOrderListView()
            case OrderListState.State.success :   SuccessOrderListView(orderList: orderListState.orderList.map({ lightOrder in
                OrderItem(id: lightOrder.uuid, status: lightOrder.status, code: lightOrder.code, dateTime: dateUtil.getDateTimeString(dateTime: lightOrder.dateTime))
            }))
            default:
                EmptyView()
            }
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_MY_ORDERS)
        )
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.observeOrders()
            
            viewModel.orderListState.watch { orderListVM in
                if(orderListVM != nil ){
                    orderListState = orderListVM!
                }
                // work with actions
            }
        }
        .onDisappear(){
            viewModel.stopObserveOrders()
        }
    }
}

struct OrderListView_Previews: PreviewProvider {
    static var previews: some View {
        OrderListView()
    }
}

struct SuccessOrderListView: View {
    let orderList : [OrderItem]
    
    var body: some View {
        ScrollView {
            LazyVStack(spacing:0){
                ForEach(orderList){ order in
                    OrderItemView(
                        orderItem:  order,
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
        VStack(spacing: 0){
            Spacer()
            
            DefaultImage(imageName: "EmptyPage")
            
            Text(Strings.MSG_ORDER_LIST_EMPTY_ORDERS).multilineTextAlignment(.center)
            Spacer()
        }
    }
}
