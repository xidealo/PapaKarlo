//
//  ConsumerCartViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

class ConsumerCartViewModel : ObservableObject  {
    
    @Published var consumerCartViewState:ConsumerCartViewState = ConsumerCartViewState(forFreeDelivery: "", cartProductList: [], oldTotalCost: nil, newTotalCost: "")

    init(){
         
        iosComponent.provideCartProductInteractor().getConsumerCartWithProducts { cartWithProducts, error in
            self.consumerCartViewState = self.getConsumerCartViewState(cartWithProducts: cartWithProducts)
        }
        
    }
    
    
    func getConsumerCartViewState(cartWithProducts:CartWithProducts?) -> ConsumerCartViewState {
        return ConsumerCartViewState(forFreeDelivery: String(cartWithProducts?.forFreeDelivery ?? 0), cartProductList: getCartProductItems(cartWithProducts: cartWithProducts), oldTotalCost: cartWithProducts?.oldTotalCost as? Int, newTotalCost: String(cartWithProducts?.newTotalCost ?? 0)
        )
    }
    
    func getCartProductItems(cartWithProducts:CartWithProducts?) -> [CartProductItem]{
        return cartWithProducts?.cartProductList.map({ lightCartProduct in
            CartProductItem(id: lightCartProduct.uuid, name: lightCartProduct.name, newCost: String(lightCartProduct.newCost), oldCost: lightCartProduct.oldCost as? Int ?? 0, photoLink: lightCartProduct.photoLink, count: Int(lightCartProduct.count) , menuProductUuid: lightCartProduct.menuProductUuid)
        }) ?? []
    }
    
    func plusProduct(productUuid:String){
        iosComponent.provideCartProductInteractor().addProductToCart(menuProductUuid: productUuid) { cartProduct, error in
            //checkNotNull CartPr
        }
    }
    
    func minusProduct(productUuid:String){
        iosComponent.provideCartProductInteractor().removeProductFromCart(menuProductUuid: productUuid) { unit, error in
            //stub
        }
    }
}
