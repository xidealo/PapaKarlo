//
//  SelectCity.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI
    import shared
struct SelectCityView: View {
    @StateObject private var viewModel = SelectCityViewModel()
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_SELECT_CITY_CITY,  cost: "220 R", count: "2", isShowBackArrow: false, isCartVisible: false, isLogoutVisible: false)
        
//            switch (viewModel.cityListState) {
//                case .loading:
//                    LoadingView()
//                case .success(let cityList):
//                    SelectCitySuccessView(cityList: cityList)
//                case .empty:
//                    LoadingView()
//                case .error(let error):
//                    LoadingView()
//            }
        }
        .background(Color("background"))
        .navigationBarHidden(true)
    }
}

struct SelectCitySuccessView : View {
    
    let cityList: [CityItem]
    
    var body: some View {
        ScrollView {
            LazyVStack{
                ForEach(cityList){ city in
                    NavigationLink(
                        destination:ContainerView()
                    ){
                        CityItemView(city: city).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                }
            }
        }.padding(.top, Diems.MEDIUM_PADDING)
        
    }
}

struct SelectCitySuccessView_Previews: PreviewProvider {
    static var previews: some View {
        SelectCityView()
    }
}
