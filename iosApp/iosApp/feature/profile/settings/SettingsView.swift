//
//  SettingsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import shared
import SwiftUI

struct SettingsView: View, SharedLifecycleWithState {
    
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

    @State var state = SettingsViewState(
        phoneNumber: "",
        selectedCityName: "",
        state: SettingsViewState.State.loading
    )

    @State private var showingDeleteAlert = false
    @State private var showingLogoutAlert = false
    
    @State var goToSelectCity: Bool = false

    @State var listener: Closeable?
    @State var eventsListener: Closeable?
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: "titleSettings",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            NavigationLink(
                destination: ChangeCityView(),
                isActive: $goToSelectCity
            ) {
                EmptyView()
            }
            .isDetailLink(false)
            
            if state.state == SettingsViewState.State.loading {
                LoadingView()
            } else {
                VStack(spacing: 0) {
                    TextCardWithDivider(
                        placeHolder: Strings.HINT_SETTINGS_PHONE,
                        text: state.phoneNumber
                    )
                    
                    NavigationCardWithDivider(
                        icon: nil,
                        label: Strings.HINT_SETTINGS_CITY,
                        value: state.selectedCityName,
                        action: {
                            viewModel.onAction(action: SettingsStateActionOnCityClicked())
                        }
                    )
                    .padding(.horizontal, 16)
                    
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
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
        .onAppear {
            viewModel.loadData()
            subscribe()
        }
        .onDisappear {
            unsubscribe()
        }
    }
    
    func subscribe() {
        listener = viewModel.dataState.watch { settingsStateVM in
            if let notNullSettingsStateVM = settingsStateVM {
                state = SettingsViewState(
                    phoneNumber: notNullSettingsStateVM.settings?.phoneNumber ?? "",
                    selectedCityName: notNullSettingsStateVM.selectedCity?.name ?? "",
                    state: {
                        switch notNullSettingsStateVM.state {
                        case SettingsStateDataState.State.success:
                            return SettingsViewState.State.success
                        case SettingsStateDataState.State.error:
                            return SettingsViewState.State.error
                        case SettingsStateDataState.State.loading:
                            return SettingsViewState.State.loading
                        default:
                            return SettingsViewState.State.loading
                        }
                    }()
                )
            }
        }
    }
    func eventsSubscribe() {
        
        listener = viewModel.events.watch { _events in
            if let events = _events {
                let settingsEvents = events as? [SettingsStateEvent] ?? []
                
                for event in settingsEvents {
                    switch event {
                    case is SettingsStateEventBack: self.mode.wrappedValue.dismiss()
                    case is SettingsStateEventShowCityListEvent: goToSelectCity = true
                    default:
                        print("def")
                    }
                }
                
                if !settingsEvents.isEmpty {
                    viewModel.consumeEvents(events: settingsEvents)
                }
            }
        }
    }
    
    func unsubscribe() {
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
