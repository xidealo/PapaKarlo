//
//  ConsumerCartView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

import shared

struct ConsumerCartView: View {
    
    @State var viewModel: ConsumerCartViewModel = ConsumerCartViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        addCartProductUseCase: iosComponent.provideAddCartProductUseCase(),
        removeCartProductUseCase: iosComponent.provideRemoveCartProductUseCase(),
        getRecommendationsUseCase: iosComponent.provideGetRecommendationsUseCase()
    )
    
    @State var consumerCartData: ConsumerCartData? = nil
    @State var screenState: ConsumerCartStateScreenState = ConsumerCartStateScreenState.loading
    
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    //State
    @State var openCreateOrder:Bool = false
    @State var openLogin:Bool = false
    // ---
    
    //for back after createOrder
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    //--
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleCartProducts",
                back: {
                    viewModel.handleAction(action: ConsumerCartStateActionBackClick())
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
            switch screenState{
            case ConsumerCartStateScreenState.success:
                if let consumerCartUi = consumerCartData {
                    ConsumerCartSuccessScreen(
                        consumerCartUI: consumerCartUi,
                        action: viewModel.handleAction
                    )
                }
                
            case ConsumerCartStateScreenState.loading: LoadingView()
            case ConsumerCartStateScreenState.empty: ConsumerCartEmptyScreen(
                isRootActive: $isRootActive, selection: $selection
            )
            case ConsumerCartStateScreenState.error: EmptyView()
            default:
                EmptyView()
            }
        }
        .background(AppColor.background2)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
    }
    
    func subscribe(){
        viewModel.handleAction(action: ConsumerCartStateActionInit())
        listener = viewModel.state.watch { consumerCartStateVM in
            if let consumerCartStateVM =  consumerCartStateVM {
                consumerCartData = consumerCartStateVM.consumerCartData
                screenState = consumerCartStateVM.screenState
            }
        }
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let consumerCartEvents = events as? [ConsumerCartStateEvent] ?? []
                
                consumerCartEvents.forEach { event in
                    print(event)
                    switch(event){
                    case is ConsumerCartStateEventNavigateBack : self.mode.wrappedValue.dismiss()
                    case is ConsumerCartStateEventNavigateToCreateOrder : openCreateOrder = true
                    case is ConsumerCartStateEventNavigateToLogin: openLogin = true
                    default:
                        print("def")
                    }
                }
                
                if !consumerCartEvents.isEmpty {
                    viewModel.consumeEvents(events: consumerCartEvents)
                }
            }
        })
    }
    
    func unsubscribe(){
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct ConsumerCartSuccessScreen: View {
    
    let consumerCartUI : ConsumerCartData
    let cartProductListIos : [CartProductItemIos]
    
    let action: (ConsumerCartStateAction) -> Void
    
    init(consumerCartUI: ConsumerCartData, action: @escaping (ConsumerCartStateAction) -> Void) {
        self.consumerCartUI = consumerCartUI
        self.cartProductListIos = consumerCartUI.cartProductList.map({ cartProductItem in
            CartProductItemIos(
                id: cartProductItem.uuid,
                cartProductItem: cartProductItem
            )
        })
        self.action = action
    }
    
    var body: some View {
        VStack(spacing:0){
            ZStack(alignment: .bottom){
                ScrollView {
                    LazyVStack(spacing:0){
                        Text("Бесплатная доставка от \(consumerCartUI.forFreeDelivery)")
                            .bodyMedium()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.bottom, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.SMALL_PADDING)
                        
                        ForEach(cartProductListIos){ cartProductItemIos in
                            VStack(spacing:0){
                                CartProductView(cartProductItem: cartProductItemIos.cartProductItem, plusAction: {
                                    action(
                                        ConsumerCartStateActionAddProductToCartClick(menuProductUuid: cartProductItemIos.cartProductItem.menuProductUuid)
                                    )
                                }, minusAction: {
                                    action(ConsumerCartStateActionRemoveProductFromCartClick(menuProductUuid: cartProductItemIos.cartProductItem.menuProductUuid))
                                })
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.bottom, 8)

                                Divider()
                                    .frame(height: 2)
                                    .overlay(AppColor.stroke)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.bottom, 8)
                            }
                        }
                       // .padding(.bottom, Diems.MEDIUM_PADDING)
                    }
                }
            }
            
            VStack(spacing:0){
                if let discount = consumerCartUI.firstOrderDiscount{
                    HStack(spacing:0){
                        Text("consumer_cart_discount")
                            .bodyMedium()
                            .foregroundColor(AppColor.onSurface)
                        
                        Spacer()
                        
                        DiscountCard(text:discount)
                    }.padding(.top, 16)
                        .padding(.horizontal, 16)
                }
                
                HStack(spacing:0){
                    Text("consumer_cart_total")
                        .bodyMedium(weight: .bold)
                        .foregroundColor(AppColor.onSurface)
                    
                    Spacer()
                    
                    if let oldTotalCost = consumerCartUI.oldTotalCost{
                        Text(oldTotalCost)
                            .strikethrough()
                            .bodyMedium(weight: .bold)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .padding(.trailing, 4)
                    }
                    
                    Text(consumerCartUI.newTotalCost)
                        .bodyMedium(weight: .bold)
                        .foregroundColor(AppColor.onSurface)
                }.padding(.top, 8)
                    .padding(.horizontal, 16)
                
                Button {
                    action(ConsumerCartStateActionOnCreateOrderClick())
                } label: {
                    ButtonText(text: Strings.ACTION_CART_PRODUCT_CREATE_ORDER)
                }
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.vertical, Diems.MEDIUM_PADDING)
            }.background(AppColor.surface)
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
            
            EmptyWithIconView(
                imageName:  "CartIcon",
                title: "emptyCartTitleProfile",
                secondText: "emptyCartSecondProfile"
            )
            
            Spacer()
            
            Button {
                isRootActive = false
                selection = 1
            } label: {
                ButtonText(text: Strings.ACTION_CART_PRODUCT_MENU)
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}

struct CartProductItemIos : Identifiable {
    var id: String
    var cartProductItem : CartProductItem
}
