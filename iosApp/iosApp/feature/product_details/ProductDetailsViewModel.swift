//
//  ProductDetailsViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.07.2022.
//

import Foundation
import shared

class ProductDetailsViewModel : ToolbarViewModel {
    
    @Published var productDetailsViewState:ProductDetailsViewState = ProductDetailsViewState(
        name: "", size: "", oldPrice: nil, newPrice: "", description: "", imageLink: ""
    )
    
    init(productUuid:String){
        super.init()
        iosComponent.provideMenuInteractor().getMenuProductByUuid(menuProductUuid: productUuid) { menuProduct, err  in
            self.productDetailsViewState = self.getProductDetailsViewState(menuProduct: menuProduct!)
        }
    }
    
    func getProductDetailsViewState(menuProduct:MenuProduct) -> ProductDetailsViewState {
        return ProductDetailsViewState(name: menuProduct.name, size: getSize(menuProduct: menuProduct), oldPrice: menuProduct.oldPrice as? Int, newPrice: String(menuProduct.newPrice) + Strings.CURRENCY, description: menuProduct.description_, imageLink: menuProduct.photoLink)
    }
    
    func getSize(menuProduct:MenuProduct) -> String {
        if(menuProduct.nutrition == nil){
            return ""
        }else{
            return "\(menuProduct.nutrition ?? 0) \(menuProduct.utils ?? "")"
        }
    }
    
    func addCartProductToCart(menuProductUuid:String){
        iosComponent.provideCartProductInteractor().addProductToCart(menuProductUuid: menuProductUuid) { cartProduct, error in
            if(cartProduct == nil){
                print("Not added")
            }
            else{
                print("Added")
            }
        }
    }
}

