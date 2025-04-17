//
//  CreateAddressView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import shared
import SwiftUI

struct CreateAddressView: View {
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var showSuggetionLoadingError: Bool = false
    @State var showCreateError: Bool = false

    @Binding var show: Bool

    @FocusState private var isTextFieldFocused: Bool

    @State var viewModel = CreateAddressViewModel(
        getSuggestionsUseCase: iosComponent.provideGetSuggestionsUseCase(),
        createAddressUseCase: iosComponent.provideCreateAddressUseCase(),
        saveSelectedUserAddressUseCase: iosComponent.provideSaveSelectedUserAddressUseCase()
    )

    // Listeners
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    // -----

    @State var createAddressViewState = CreateAddressViewState(
        street: "",
        streetError: nil,
        streetSuggestionList: [],
        isSuggestionLoading: false,
        house: "",
        houseError: nil,
        flat: "",
        entrance: "",
        floor: "",
        comment: "",
        isCreateLoading: false
    )

    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: "titleCreationAddress",
                back: {
                    viewModel.onAction(action: CreateAddressActionBackClick())
                }
            )
            ZStack(alignment: .bottom) {
                ScrollView {
                    VStack(spacing: 0) {
                        SearchEditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_STREET,
                            text: $createAddressViewState.street,
                            limit: 100,
                            list: createAddressViewState.streetSuggestionList,
                            errorMessage: $createAddressViewState.streetError,
                            isLoading: $createAddressViewState.isSuggestionLoading,
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionStreetTextChange(street: changedValue))
                            },
                            selectSuggetion: { streetItem in
                                viewModel.onAction(action: CreateAddressActionSuggestionSelect(
                                    suggestion: SuggestionUi(
                                        id: streetItem.id,
                                        value: streetItem.name,
                                        postfix: streetItem.postfix
                                    )
                                )
                                )
                            },
                            focusChangeListener: { isSelected in
                                viewModel.onAction(action: CreateAddressActionStreetFocusChange(isFocused: isSelected))
                            }
                        )
                        .focused($isTextFieldFocused)

                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_HOUSE,
                            text: $createAddressViewState.house,
                            limit: 5,
                            errorMessage: $createAddressViewState.houseError,
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionHouseTextChange(house: changedValue))
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)

                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_FLAT,
                            text: $createAddressViewState.flat,
                            limit: 5,
                            errorMessage: .constant(nil),
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionFlatTextChange(flat: changedValue))
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)

                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE,
                            text: $createAddressViewState.entrance,
                            limit: 5,
                            errorMessage: .constant(nil),
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionEntranceTextChange(entrance: changedValue))
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)

                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_FLOOR,
                            text: $createAddressViewState.floor,
                            limit: 5,
                            errorMessage: .constant(nil),
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionFloorTextChange(floor: changedValue))
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)

                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_COMMENT,
                            text: $createAddressViewState.comment,
                            limit: 100,
                            errorMessage: .constant(nil),
                            textChanged: { changedValue in
                                viewModel.onAction(action: CreateAddressActionCommentTextChange(comment: changedValue))
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                    }
                    .padding(Diems.MEDIUM_PADDING)
                    .background(AppColor.surface)
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .padding(Diems.MEDIUM_PADDING)
                    .padding(.bottom, isTextFieldFocused ? 60 : 0)
                }

                Button(
                    action: {
                        viewModel.onAction(action: CreateAddressActionSaveClick())
                    }
                ) {
                    if createAddressViewState.isCreateLoading {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: AppColor.primary))
                            .scaleEffect(1.2)
                    } else {
                        ButtonText(text: Strings.ACTION_CREATION_ADDRESS_ADD)
                    }
                }
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .padding(.bottom, Diems.MEDIUM_PADDING)
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Не удалось добавить адрес"),
                    show: $showCreateError,
                    backgroundColor: AppColor.error,
                    foregroundColor: AppColor.onError
                ),
                show: $showCreateError
            )
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Не удалось загрузить список улиц"),
                    show: $showSuggetionLoadingError,
                    backgroundColor: AppColor.error,
                    foregroundColor: AppColor.onError
                ),
                show: $showSuggetionLoadingError
            )
        }
        .hiddenNavigationBarStyle()
        .background(AppColor.background)
        .onAppear {
            subscribe()
            eventsSubscribe()
        }
        .onDisappear {
            unsubscribe()
        }
    }

    func subscribe() {
        viewModel.onAction(action: CreateAddressActionInit())
        viewModel.dataState.watch { createAddressVM in
            if let createAddressDataState = createAddressVM {
                print(createAddressDataState)
                createAddressViewState = CreateAddressViewState(
                    street: createAddressDataState.street,
                    streetError: getStreetError(createAddressDataState: createAddressDataState),
                    streetSuggestionList: createAddressDataState.streetSuggestionList.map { streetItem in
                        StreetItem(
                            id: streetItem.id,
                            name: streetItem.value,
                            postfix: streetItem.postfix
                        )
                    },
                    isSuggestionLoading: createAddressDataState.isSuggestionLoading,
                    house: createAddressDataState.house,
                    houseError: getHouseError(createAddressDataState: createAddressDataState),
                    flat: createAddressDataState.flat,
                    entrance: createAddressDataState.entrance,
                    floor: createAddressDataState.floor,
                    comment: createAddressDataState.comment,
                    isCreateLoading: createAddressDataState.isCreateLoading
                )
            }
        }
    }

    func getStreetError(createAddressDataState: CreateAddressDataState) -> LocalizedStringKey? {
        if createAddressDataState.hasStreetError {
            return LocalizedStringKey("error_create_address_street")
        }
        return nil
    }

    func getHouseError(createAddressDataState: CreateAddressDataState) -> LocalizedStringKey? {
        if createAddressDataState.hasHouseError {
            return LocalizedStringKey("error_create_address_house")
        }
        return nil
    }

    func eventsSubscribe() {
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events {
                let createAddressStateEvents = events as? [CreateAddressEvent] ?? []

                for event in createAddressStateEvents {
                    switch event {
                    case is CreateAddressEventBack: self.mode.wrappedValue.dismiss()
                    case is CreateAddressEventAddressCreatedSuccess: self.mode.wrappedValue.dismiss()
                    case is CreateAddressEventSuggestionLoadingFailed: showSuggetionLoadingError = true
                    case is CreateAddressEventAddressCreatedFailed: showCreateError = true
                    default:
                        print("def")
                    }
                }

                if !createAddressStateEvents.isEmpty {
                    viewModel.consumeEvents(events: createAddressStateEvents)
                }
            }
        })
    }

    func unsubscribe() {
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}
