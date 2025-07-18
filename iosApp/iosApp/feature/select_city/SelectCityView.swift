//
//  SelectCityView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import shared
import SwiftUI

struct SelectCityView: View {
    @ObservedObject private var viewModel = SelectCityViewModel()
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing: 0) {
            NavigationLink(
                destination: MenuView(),
                isActive: $viewModel.selectCityViewState.isGoToMenu
            ) {
                EmptyView()
            }.isDetailLink(false)

            ToolbarView(
                title: "titleSelectCity"
            )
            if viewModel.selectCityViewState.isLoading {
                LoadingView()
            } else {
                SelectCitySuccessView(
                    cityList: viewModel.selectCityViewState.cityList,
                    viewModel: viewModel
                )
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
    }
}

struct SelectCitySuccessView: View {
    let cityList: [CityItem]
    let viewModel: SelectCityViewModel

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(cityList) { city in
                    Button {
                        viewModel.saveSelectedCity(city: city.city)
                    } label: {
                        CityItemView(city: city)
                            .padding(.bottom, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                }
            }
        }
        .padding(.top, Diems.MEDIUM_PADDING)
    }
}

struct SelectCitySuccessView_Previews: PreviewProvider {
    static var previews: some View {
        SelectCitySuccessView(cityList: [CityItem(city: City(uuid: "uuid", name: "name", timeZone: "+3")), CityItem(city: City(uuid: "ss", name: "Dubna", timeZone: "+3"))], viewModel: SelectCityViewModel())
    }
}
