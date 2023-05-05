//
//  ConsumerCartViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

class ConsumerCartViewModel : ObservableObject  {
    
    @Published var consumerCartViewState : ConsumerCartViewState = ConsumerCartViewState(
        forFreeDelivery: "",
        cartProductList: [],
        oldTotalCost: nil,
        newTotalCost: "",
        consumerCartState: ConsumerCartState.loading,
        actions: []
    )

    func fetchData(){
        (consumerCartViewState.copy() as! ConsumerCartViewState).apply { newState in
            newState.consumerCartState = ConsumerCartState.loading
            consumerCartViewState = newState
        }
        
        iosComponent.provideCartProductInteractor().observeConsumerCart().watch{ consumerCart in
            if(consumerCart is ConsumerCart.WithProducts){
                self.consumerCartViewState = self.getConsumerCartViewState(cartWithProducts: consumerCart as? ConsumerCart.WithProducts)
            }else{
                (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
                    copiedState.cartProductList = []
                    copiedState.consumerCartState = ConsumerCartState.empty
                    self.consumerCartViewState = copiedState
                }
            }
        }
    }

    func getConsumerCartViewState(cartWithProducts:ConsumerCart.WithProducts?) -> ConsumerCartViewState {
        return ConsumerCartViewState(
            forFreeDelivery: String(cartWithProducts?.forFreeDelivery ?? 0),
            cartProductList: getCartProductItems(cartWithProducts: cartWithProducts),
            oldTotalCost: cartWithProducts?.oldTotalCost as? Int,
            newTotalCost: String(cartWithProducts?.newTotalCost ?? 0) + Strings.CURRENCY,
            consumerCartState: ConsumerCartState.hasData,
            actions: []
        )
    }
    
    func getCartProductItems(cartWithProducts:ConsumerCart.WithProducts?) -> [CartProductItem]{
        return cartWithProducts?.cartProductList.map({ lightCartProduct in
            CartProductItem(
                id: lightCartProduct.uuid,
                name: lightCartProduct.name,
                newCost: String(lightCartProduct.newCost) + Strings.CURRENCY,
                oldCost: lightCartProduct.oldCost as? Int,
                photoLink: lightCartProduct.photoLink,
                count: Int(lightCartProduct.count),
                menuProductUuid: lightCartProduct.menuProductUuid
            )
        }) ?? []
    }
    
    func plusProduct(productUuid:String){
        iosComponent.provideCartProductInteractor().addProductToCart(menuProductUuid: productUuid) { cartProduct, error in
            print("plus product \(productUuid)")
        }
    }
    
    func minusProduct(productUuid:String){
        iosComponent.provideRemoveCartProductUseCase().invoke(menuProductUuid: productUuid) {_, err  in
            print("minus product \(productUuid)")
        }
    }
    
    func checkAuthorization(){
        iosComponent.provideIUserInteractor().isUserAuthorize { isAuthorized, err in
            if(isAuthorized == true){
                (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
                    copiedState.cartProductList = []
                    copiedState.actions.append(ConsumerCartAction.openCreateOrderAction)
                    self.consumerCartViewState = copiedState
                }
            }else{
                (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
                    copiedState.cartProductList = []
                    copiedState.actions.append(ConsumerCartAction.openLoginAction)
                    self.consumerCartViewState = copiedState
                }
            }
        }
    }
    
    func consumeActions(){
        (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
            copiedState.actions = []
            self.consumerCartViewState = copiedState
        }
    }
}

enum ConsumerCartAction {
    case openCreateOrderAction, openLoginAction
}
