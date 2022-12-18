//
//  ViewModelStore.swift
//  iosApp
//
//  Created by Марк Шавловский on 25.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

var viewModelStore = ViewModelStore()

class ViewModelStore{
    
    var consumerCartViewModel : ConsumerCartViewModel? = nil
    var menuViewModel : MenuViewModel? = nil
    var cafeListViewModel : CafeViewModel? = nil

     func getConsumerCartViewModel() -> ConsumerCartViewModel {
        if(consumerCartViewModel == nil){
            let newConsumerCartViewModel = ConsumerCartViewModel()
            self.consumerCartViewModel = newConsumerCartViewModel
            return newConsumerCartViewModel
        }
        return consumerCartViewModel!
    }
    
    func getMenuViewModel() -> MenuViewModel {
       if(menuViewModel == nil){
           let newConsumerCartViewModel = MenuViewModel()
           self.menuViewModel = newConsumerCartViewModel
           return newConsumerCartViewModel
       }
       return menuViewModel!
   }
    
    func getCafeListViewModelViewModel() -> CafeViewModel {
       if(cafeListViewModel == nil){
           let newConsumerCartViewModel = CafeViewModel()
           self.cafeListViewModel = newConsumerCartViewModel
           return newConsumerCartViewModel
       }
       return cafeListViewModel!
   }
}
