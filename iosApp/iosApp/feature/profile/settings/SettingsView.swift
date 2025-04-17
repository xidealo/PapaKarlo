//
//  SettingsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import shared
import SwiftUI

struct SettingsView: View {
    var viewModel = SettingsViewModel(
        observeSettingsUseCase: iosComponent.provideObserveSettingsUseCase(),
        observeSelectedCityUseCase: iosComponent.provideObserveSelectedCityUseCase(),
        updateEmailUseCase: iosComponent.provideUpdateEmailUseCase(),
        getCityListUseCase: iosComponent.provideGetCityListUseCase(),
        saveSelectedCityUseCase: iosComponent.provideSaveSelectedCityUseCase(),
        disableUserUseCase: iosComponent.provideDisableUserUseCase(),
        userInteractor: iosComponent.provideIUserInteractor(),
        analyticService: iosComponent.provideAnalyticService()
    )

    @State var state = SettingsState(
        settings: nil,
        selectedCity: nil,
        cityList: [],
        state: SettingsState.State.loading,
        eventList: []
    )

    @State private var showingDeleteAlert = false
    @State private var showingLogoutAlert = false

    @State var listener: Closeable? = nil

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: "titleSettings",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )

            if state.state == SettingsState.State.loading {
                LoadingView()
            } else {
                VStack(spacing: 0) {
                    TextCard(
                        placeHolder: Strings.HINT_SETTINGS_PHONE,
                        text: state.settings?.phoneNumber ?? ""
                    )
                    // TODO(Add Email in next Version)

                    NavigationTextCard(
                        placeHolder: Strings.HINT_SETTINGS_CITY,
                        text: state.selectedCity?.name ?? "",
                        destination: ChangeCityView()
                    )
                    .padding(.top, Diems.SMALL_PADDING)

                    Button(action: {
                        showingDeleteAlert = true
                    }) {
                        Text(Strings.ACTION_SETTINGS_REMOVE_ACCOUNT)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .foregroundColor(AppColor.error)
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                    }.alert("Вы уверены, что хотите удалить аккаунт?", isPresented: $showingDeleteAlert) {
                        Button("Да") {
                            viewModel.disableUser()
                        }
                        Button("Нет", role: .cancel) {}
                    }
                    .padding(.top, 16)
                }
                .padding(Diems.MEDIUM_PADDING)

                Spacer()

                Button(
                    action: {
                        showingLogoutAlert = true
                    }, label: {
                        ButtonText(text: "Выйти")
                    }
                ).padding(16)
                    .alert("Выйти из профиля?", isPresented: $showingLogoutAlert) {
                        Button("Выйти") {
                            viewModel.logout()
                        }
                        Button("Отмена", role: .cancel) {}
                    }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear {
            viewModel.loadData()
            listener = viewModel.settingsState.watch { settingsStateVM in
                if let notNullSettingsStateVM = settingsStateVM {
                    state = notNullSettingsStateVM
                    for event in notNullSettingsStateVM.eventList {
                        switch event {
                        case is SettingsStateEventBack: self.mode.wrappedValue.dismiss()
                        default:
                            print("def")
                        }
                    }
                    if !notNullSettingsStateVM.eventList.isEmpty {
                        viewModel.consumeEventList(eventList: settingsStateVM!.eventList)
                    }
                }
            }
        }
        .onDisappear {
            listener?.close()
            listener = nil
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
