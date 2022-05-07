//
//  SelectCityViewModel.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import Foundation

extension SelectCityView {
    @MainActor class ViewModel: ObservableObject {
        @Published private(set) var cityListState = StateUI<[CityItem]>.success([CityItem(city: "Кимры"), CityItem(city: "Дубна")])
    }
}
