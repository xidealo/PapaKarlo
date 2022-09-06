//
//  ConsumerCartViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

var consumerCartView = ConsumerCartView()

class ConsumerCartViewModel : ObservableObject  {
    
    @Published var consumerCartViewState:ConsumerCartViewState = ConsumerCartViewState(forFreeDelivery: "", cartProductList: [], oldTotalCost: nil, newTotalCost: "", consumerCartState: ConsumerCartState.loading)
    
    var closable : Ktor_ioCloseable? = nil
    
    init(){
        print("CreateOrderViewModel")
    }
    
    func fetchData(){
        print("Fetch data ConsumerCartViewModel")
        (consumerCartViewState.copy() as! ConsumerCartViewState).apply { newState in
            newState.consumerCartState = ConsumerCartState.loading
            consumerCartViewState = newState
        }
        
        closable = iosComponent.provideCartProductInteractor().observeConsumerCart().watch{ consumerCart in
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
    
    func removeListener(){
        closable?.close()
    }
    
    func getConsumerCartViewState(cartWithProducts:ConsumerCart.WithProducts?) -> ConsumerCartViewState {
        return ConsumerCartViewState(forFreeDelivery: String(cartWithProducts?.forFreeDelivery ?? 0), cartProductList: getCartProductItems(cartWithProducts: cartWithProducts), oldTotalCost: cartWithProducts?.oldTotalCost as? Int, newTotalCost: String(cartWithProducts?.newTotalCost ?? 0) + Strings.CURRENCY, consumerCartState: ConsumerCartState.hasData
        )
    }
    
    func getCartProductItems(cartWithProducts:ConsumerCart.WithProducts?) -> [CartProductItem]{
        return cartWithProducts?.cartProductList.map({ lightCartProduct in
            CartProductItem(id: lightCartProduct.uuid, name: lightCartProduct.name, newCost: String(lightCartProduct.newCost) + Strings.CURRENCY, oldCost: lightCartProduct.oldCost as? Int, photoLink: lightCartProduct.photoLink, count: Int(lightCartProduct.count) , menuProductUuid: lightCartProduct.menuProductUuid)
        }) ?? []
    }
    
    func plusProduct(productUuid:String){
        iosComponent.provideCartProductInteractor().addProductToCart(menuProductUuid: productUuid) { cartProduct, error in
            //checkNotNull CartPr
        }
    }
    
    func minusProduct(productUuid:String){
        iosComponent.provideCartProductInteractor().removeProductFromCart(menuProductUuid: productUuid) {err in
            //stub
        }
    }
    func checkAuthorization(){
        iosComponent.provideIUserInteractor().isUserAuthorize { isAuthorized, err in
            if(isAuthorized == true){
                (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
                    copiedState.cartProductList = []
                    copiedState.consumerCartState = ConsumerCartState.goToCreateOrder
                    self.consumerCartViewState = copiedState
                }
            }else{
                (self.consumerCartViewState.copy() as! ConsumerCartViewState).apply { copiedState in
                    copiedState.cartProductList = []
                    copiedState.consumerCartState = ConsumerCartState.goToLogin
                    self.consumerCartViewState = copiedState
                }
            }
        }
    }
}
