//
//  OrderListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct OrderListView: View {
    
    @ObservedObject private var viewModel  = OrderListViewModel()
    
    var body: some View {
        VStack{
            if(viewModel.orderListViewState.orderList.count == 0){
                EmptyOrderListView()
            }else{
                SuccessOrderListView(orderList: viewModel.orderListViewState.orderList)
            }
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_MY_ORDERS)
        ).navigationBarHidden(true)
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
        VStack(spacing: 0){
            ToolbarView(title: Strings.TITLE_MY_ORDERS, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            ScrollView {
                LazyVStack(spacing:0){
                    ForEach(orderList){ order in
                        OrderItemView(orderItem:  order, destination: OrderDetailsView()).padding(.vertical, Diems.HALF_SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                }
            }
        }
    }
    
}



struct EmptyOrderListView: View {
    var body: some View {
        VStack{
            Spacer()
            
            DefaultImage(imageName: "EmptyPage")
            
            Text(Strings.MSG_ORDER_LIST_EMPTY_ORDERS).multilineTextAlignment(.center)
            Spacer()
            
        }
    }
}
