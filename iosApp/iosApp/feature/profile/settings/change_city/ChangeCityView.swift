//
//  ChangeCityView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct ChangeCityView: View {
    
    @ObservedObject private var viewModel = ChangeCityViewModel()
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack{
            ToolbarView(
                title: Strings.TITLE_SELECT_CITY_CITY,
                cost: "",
                count: "",
                isCartVisible: false,
                back: {
                    self.presentationMode.wrappedValue.dismiss()
                }
            )
            
            switch(viewModel.changeCityViewState.changeCityState){
            case ChangeCityState.loading : LoadingView()
            case ChangeCityState.success : ScrollView {
                LazyVStack{
                    ForEach(viewModel.changeCityViewState.cityList){ city in
                        Button(action:{
                            viewModel.selectCity(uuid: city.id)
                        }
                        ){
                            ChangeCityItemView(city: city).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
            default:  EmptyView()
            }
        }
        .onReceive(viewModel.$changeCityViewState, perform: { state in
            if(state.changeCityState == ChangeCityState.back){
                self.presentationMode.wrappedValue.dismiss()
            }
        })
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        
    }
}

struct ChangeCityView_Previews: PreviewProvider {
    static var previews: some View {
        ChangeCityView()
    }
}
