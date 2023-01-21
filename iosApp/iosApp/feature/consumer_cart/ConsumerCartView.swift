//
//  ConsumerCartView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

struct ConsumerCartView: View {
    
    @StateObject var viewModel: ConsumerCartViewModel = ConsumerCartViewModel()
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var openCreateOrder:Bool = false
    @State var openLogin:Bool = false
    
    //for back after createOrder
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    //--
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: Strings.TITLE_CART_PRODUCTS,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            NavigationLink(
                destination:LoginView(rootIsActive: $openLogin, isGoToCreateOrder: $openCreateOrder),
                isActive: $openLogin
            ){
                EmptyView()
            }
            .isDetailLink(false)
            
            NavigationLink(
                destination:CreateOrderView(
                    isRootActive: self.$isRootActive,
                    selection: self.$selection,
                    showOrderCreated: $showOrderCreated
                ),
                isActive: $openCreateOrder
            ){
                EmptyView()
            }
            
            switch viewModel.consumerCartViewState.consumerCartState{
            case .loading: LoadingView()
            case .notAuthorized: EmptyView()
            case .empty: ConsumerCartEmptyScreen(
                isRootActive: $isRootActive, selection: $selection
            )
            case .hasData: ConsumerCartSuccessScreen(consumerCartUI: viewModel.consumerCartViewState, viewModel: viewModel)
            }
        }
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear() {
            viewModel.fetchData()
        }
        .onReceive(viewModel.$consumerCartViewState, perform: { consumerCartViewState in
            consumerCartViewState.actions.forEach { action in
                switch(action){
                case ConsumerCartAction.openLoginAction : openLogin = true
                case ConsumerCartAction.openCreateOrderAction : openCreateOrder = true
                }
                
                if !consumerCartViewState.actions.isEmpty{
                    viewModel.consumeActions()
                }
            }
        })
    }
}

struct ConsumerCartSuccess_Previews: PreviewProvider {
    static var previews: some View {
        ConsumerCartSuccessScreen(consumerCartUI: ConsumerCartViewState(forFreeDelivery: "100", cartProductList: [CartProductItem(id: "1", name: "Burger", newCost: "100", oldCost: nil, photoLink: "https://canapeclub.ru/buffet-sets/burger/bolshoy-burger", count: 10, menuProductUuid: "uuid")], oldTotalCost: nil, newTotalCost: "100", consumerCartState: ConsumerCartState.hasData, actions: []), viewModel: ConsumerCartViewModel())
    }
}

struct ConsumerCartSuccessScreen: View {
    
    let consumerCartUI : ConsumerCartViewState
    @ObservedObject var viewModel : ConsumerCartViewModel
    
    var body: some View {
        VStack(spacing:0){
            ZStack(alignment: .bottom){
                ScrollView {
                    LazyVStack(spacing:0){
                        Text("Бесплатная доставка от \(consumerCartUI.forFreeDelivery)\(Strings.CURRENCY)")
                            .frame(maxWidth: .infinity, alignment: .center)
                            .padding(.bottom, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.SMALL_PADDING)
                        VStack(spacing: 0){
                            ForEach(consumerCartUI.cartProductList){ cartProductItem in
                                CartProductView(cartProductItem: cartProductItem, plusAction: {
                                    viewModel.plusProduct(productUuid: cartProductItem.menuProductUuid)
                                }, minusAction: {
                                    viewModel.minusProduct(productUuid: cartProductItem.menuProductUuid)
                                })
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.bottom, Diems.SMALL_PADDING)
                            }
                        }
                        .padding(.bottom, Diems.MEDIUM_PADDING)
                    }
                }
                LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom)
                    .frame(height:20)
            }
            
            VStack(spacing:0){
                HStack(spacing:0){
                    BoldText(text: Strings.MSG_CART_PRODUCT_RESULT)
                    Spacer()
                    
                    if consumerCartUI.oldTotalCost != nil{
                        StrikeText(text: String(consumerCartUI.oldTotalCost!) + Strings.CURRENCY)
                    }
                    BoldText(text: consumerCartUI.newTotalCost)
                        .padding(Diems.SMALL_PADDING)
                }.padding()
                
                Button {
                    viewModel.checkAuthorization()
                } label: {
                    Text(Strings.ACTION_CART_PRODUCT_CREATE_ORDER).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.bottom, Diems.MEDIUM_PADDING)
            }.background(Color("surface"))
        }
    }
}

struct ConsumerCartEmptyScreen: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    
    var body: some View {
        VStack(spacing:0){
            Spacer()
            
            DefaultImage(imageName: "runMan")
            
            Text(Strings.MSG_CART_PRODUCT_EMPTY)
                .multilineTextAlignment(.center)
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)

            Spacer()
            
            Button {
                isRootActive = false
                selection = 1
            } label: {
                Text(Strings.ACTION_CART_PRODUCT_MENU)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}
