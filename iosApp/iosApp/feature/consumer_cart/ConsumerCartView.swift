//
//  ConsumerCartView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

import shared

struct ConsumerCartView: View {
    
    let viewModel: ConsumerCartViewModel = ConsumerCartViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        addCartProductUseCase: iosComponent.provideAddCartProductUseCase(),
        removeCartProductUseCase: iosComponent.provideRemoveCartProductUseCase(),
        getRecommendationsUseCase: iosComponent.provideApiRepo()
        analyticService: iosComponent.provideAnalyticService()
    )
    
    @State var consumerCartUIState = ConsumerCartUIState(
        consumerCartState: ConsumerCartUIStateConsumerCartStateLoading(),
        eventList: []
    )
    
    @State var listener: Closeable? = nil
    
    
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
                title: "titleCartProducts",
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
            switch consumerCartUIState.consumerCartState{
            case is ConsumerCartUIStateConsumerCartStateSuccess:
                let data = (consumerCartUIState.consumerCartState as! ConsumerCartUIStateConsumerCartStateSuccess).data
                ConsumerCartSuccessScreen(
                    consumerCartUI: data,
                    plusAction: { uuid in
                        viewModel.onAddCardProductClicked(menuProductUuid: uuid)
                    },
                    minusAction: { uuid in
                        viewModel.onRemoveCardProductClicked(menuProductUuid: uuid)
                    },
                    mainButtonAction: {
                        viewModel.onCreateOrderClicked()
                    }
                )
            case is ConsumerCartUIStateConsumerCartStateLoading: LoadingView()
            case is ConsumerCartUIStateConsumerCartStateEmpty: ConsumerCartEmptyScreen(
                isRootActive: $isRootActive, selection: $selection
            )
            case is ConsumerCartUIStateConsumerCartStateError: EmptyView()
            default:
                EmptyView()
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .onChange(of : consumerCartUIState, perform: { creationOrderViewState in
            
            print(creationOrderViewState.eventList)
            
            consumerCartUIState.eventList.forEach { event in
                switch(event){
                case is ConsumerCartEventNavigateToMenuEvent : self.mode.wrappedValue.dismiss()
                case is ConsumerCartEventNavigateToCreateOrderEvent : openCreateOrder = true
                case is ConsumerCartEventNavigateToLoginEvent : openLogin = true
                case is ConsumerCartEventNavigateToProductEvent : openLogin = true
                default:
                    print("def")
                }
            }
            
            if !consumerCartUIState.eventList.isEmpty{
                viewModel.consumeEventList(eventList: consumerCartUIState.eventList)
            }
        })
    }
    
    func subscribe(){
        viewModel.getConsumerCart()
        listener = viewModel.consumerCartState.watch { consumerCartStateVM in
            if(consumerCartStateVM != nil ){
                consumerCartUIState = consumerCartStateVM!
            }
        }
    }
    
    func unsubscribe(){
        listener?.close()
        listener = nil
    }
}

struct ConsumerCartSuccessScreen: View {
    
    let consumerCartUI : ConsumerCartData
    let cartProductListIos : [CartProductItemIos]
    let plusAction: (String) -> Void
    let minusAction: (String) -> Void
    let mainButtonAction: () -> Void
    
    init(consumerCartUI: ConsumerCartData, plusAction: @escaping (String) -> Void, minusAction: @escaping (String) -> Void, mainButtonAction : @escaping () -> Void) {
        self.consumerCartUI = consumerCartUI
        self.cartProductListIos = consumerCartUI.cartProductList.map({ cartProductItem in
            CartProductItemIos(
                id: cartProductItem.uuid,
                cartProductItem: cartProductItem
            )
        })
        self.plusAction = plusAction
        self.minusAction = minusAction
        self.mainButtonAction = mainButtonAction
    }
    
    var body: some View {
        VStack(spacing:0){
            ZStack(alignment: .bottom){
                ScrollView {
                    LazyVStack(spacing:0){
                        Text("Бесплатная доставка от \(consumerCartUI.forFreeDelivery)\(Strings.CURRENCY)")
                            .bodyLarge()
                            .frame(maxWidth: .infinity, alignment: .center)
                            .padding(.bottom, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.SMALL_PADDING)
                        
                        VStack(spacing: 0){
                            ForEach(cartProductListIos){ cartProductItemIos in
                                CartProductView(cartProductItem: cartProductItemIos.cartProductItem, plusAction: {
                                    plusAction(cartProductItemIos.cartProductItem.menuProductUuid)
                                }, minusAction: {
                                    minusAction(cartProductItemIos.cartProductItem.menuProductUuid)
                                })
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.bottom, Diems.SMALL_PADDING)
                            }
                        }
                        .padding(.bottom, Diems.MEDIUM_PADDING)
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
                    mainButtonAction()
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
