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
    @State var screenState: ConsumerCartScreenState = ConsumerCartScreenState.loading
    
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
                    viewModel.handleAction(action: ConsumerCartActionBackClick())
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
            case ConsumerCartScreenState.success:
                if let consumerCartUi = consumerCartData {
                    ConsumerCartSuccessScreen(
                        consumerCartUI: consumerCartUi,
                        action: viewModel.handleAction
                    )
                }
                
            case ConsumerCartScreenState.loading: LoadingView()
            case ConsumerCartScreenState.empty: ConsumerCartEmptyScreen(
                isRootActive: $isRootActive, selection: $selection
            )
            case ConsumerCartScreenState.error: EmptyView()
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
        viewModel.handleAction(action: ConsumerCartActionInit())
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
                let consumerCartEvents = events as? [ConsumerCartEvent] ?? []
                
                consumerCartEvents.forEach { event in
                    print(event)
                    switch(event){
                    case is ConsumerCartEventNavigateBack : self.mode.wrappedValue.dismiss()
                    case is ConsumerCartEventNavigateToCreateOrder : openCreateOrder = true
                    case is ConsumerCartEventNavigateToLogin: openLogin = true
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
    let recommendationProductList : [MenuProductItem]
    
    @State var selection:Int = 1 //Not used for navigate to productDetails

    let action: (ConsumerCartAction) -> Void
    
    let columns = [
        GridItem(.flexible(), spacing: 8, alignment: .top),
        GridItem(.flexible(), spacing: 8, alignment: .top)
    ]
    
    init(
        consumerCartUI: ConsumerCartData,
        action: @escaping (ConsumerCartAction) -> Void
    ) {
        self.consumerCartUI = consumerCartUI
        self.cartProductListIos = consumerCartUI.cartProductList.map({ cartProductItem in
            CartProductItemIos(
                id: cartProductItem.uuid,
                cartProductItem: cartProductItem
            )
        })
        self.action = action
        self.recommendationProductList = consumerCartUI.recommendations.map({ menuProduct in
            MenuProductItem(
                id: menuProduct.uuid,
                productUuid: menuProduct.uuid,
                name: menuProduct.name,
                newPrice: String(menuProduct.newPrice) + Strings.CURRENCY,
                oldPrice: menuProduct.oldPrice as? Int,
                photoLink: menuProduct.photoLink
            )
        })
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
                                        ConsumerCartActionAddProductToCartClick(menuProductUuid: cartProductItemIos.cartProductItem.menuProductUuid)
                                    )
                                }, minusAction: {
                                    action(ConsumerCartActionRemoveProductFromCartClick(menuProductUuid: cartProductItemIos.cartProductItem.menuProductUuid))
                                })
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.bottom, 8)
                                if(cartProductListIos.last?.id != cartProductItemIos.id){
                                    Divider()
                                        .frame(height: 2)
                                        .overlay(AppColor.stroke)
                                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                                        .padding(.bottom, 8)
                                }
                            }
                        }
                    }
                    
                    if(!recommendationProductList.isEmpty){
                        Text("consumer_cart_recommendations")
                            .titleMedium(weight: .medium)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.bottom, 8)
                            .padding(.top, 16)
                        
                    }
                
                    LazyVGrid(columns: columns, spacing: 8) {
                        ForEach(recommendationProductList){ menuProductItem in
                            MenuItemView(
                                menuProductItem: menuProductItem,
                                isRootActive : .constant(false),
                                selection : $selection,
                                showOrderCreated : .constant(false),
                                action: {
                                    action(ConsumerCartActionAddProductToRecommendationClick(menuProductUuid: menuProductItem.productUuid))
                                }
                            )
                        }
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.bottom, 8)
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
                    action(ConsumerCartActionOnCreateOrderClick())
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
