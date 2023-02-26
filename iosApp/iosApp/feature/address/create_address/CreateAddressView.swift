//
//  CreateAddressView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI
import shared


class CreateAddressHolder: ObservableObject {
    var viewModel = CreateAddressViewModel(
        getStreetsUseCase: iosComponent.provideGetStreetsUseCase(),
        createAddressUseCase: iosComponent.provideCreateAddressUseCase(),
        saveSelectedUserAddressUseCase: iosComponent.provideSaveSelectedUserAddressUseCase()
    )
}

struct CreateAddressView: View {
    
    @State var street:String = ""
    @State var house:String = ""
    @State var flat:String = ""
    @State var entarance:String = ""
    @State var floor:String = ""
    @State var comment:String = ""
    
    @State var hasStreetError:Bool = false
    @State var hasHouseError:Bool = false
    @State var hasFlatError:Bool = false
    @State var hasEntaranceError:Bool = false
    @State var hasFloorError:Bool = false
    @State var hasCommentError:Bool = false
    
    @Binding var show:Bool
    
    @FocusState private var isTextFieldFocused: Bool

    @State var showError:Bool = false
    
    @StateObject var viewModel:CreateAddressHolder = CreateAddressHolder()
    
    @State var listener: Closeable? = nil
    
    @State var createAddressState = CreateAddressState(
        streetItemList: [],
        state: CreateAddressState.StateLoading(),
        hasStreetError: false,
        houseFieldError: nil,
        hasFlatError: false,
        hasEntranceError: false,
        hasFloorError: false,
        hasCommentError: false,
        isCreateLoading: false,
        eventList: [],
        suggestedStreetList: []
    )
    
    @State var filteredList : [StreetItem] = []
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: Strings.TITLE_CREATION_ADDRESS,
                back: {
                    self.presentationMode.wrappedValue.dismiss()
                }
            )
            switch(createAddressState.state){
            case is CreateAddressState.StateLoading: LoadingView()
            case is CreateAddressState.StateSuccess: ZStack (alignment: .bottom){
                ScrollView{
                    VStack(spacing:0){
                        SearchEditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_STREET,
                            text: $street,
                            limit: 100,
                            list: $filteredList,
                            hasError: $hasStreetError,
                            errorMessage: "Выберите улицу из списка",
                            textChanged: { changedValue in
                                viewModel.viewModel.filter(query: changedValue)
                            }
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.MEDIUM_PADDING)
                        
                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_HOUSE,
                            text: $house,
                            limit: 5,
                            hasError: $hasHouseError,
                            errorMessage: "Введите номер дома"
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        
                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_FLAT,
                            text: $flat,
                            limit: 5,
                            hasError: $hasFlatError,
                            errorMessage: "Максимальная длина поля 5"
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)
                        
                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE,
                            text: $entarance,
                            limit: 5,
                            hasError: $hasEntaranceError,
                            errorMessage: "Максимальная длина поля 5"
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)
                        
                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_FLOOR,
                            text: $floor,
                            limit: 5,
                            hasError: $hasFloorError,
                            errorMessage: "Максимальная длина поля 5"
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .keyboardType(.numberPad)
                        
                        EditTextView(
                            hint: Strings.HINT_CREATION_ADDRESS_COMMENT,
                            text: $comment,
                            limit: 100,
                            hasError: $hasCommentError,
                            errorMessage: "Максимальная длина поля 100"
                        )
                        .focused($isTextFieldFocused)
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.bottom, isTextFieldFocused ? 60 :  0)
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }
                                
                Button(
                    action: {
                        viewModel.viewModel.onCreateAddressClicked(
                            streetName: street,
                            house: house,
                            flat: flat,
                            entrance: entarance,
                            floor: floor,
                            comment: comment
                        )
                    }
                ) {
                    if(createAddressState.isCreateLoading){
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: Color("primary")))
                            .scaleEffect(1.5)
                    }else{
                        ButtonText(text: Strings.ACTION_CREATION_ADDRESS_ADD)
                    }
                }
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .padding(.bottom, Diems.MEDIUM_PADDING)
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Что-то пошло не так"),
                    show: $showError,
                    backgroundColor: Color("errorColor"),
                    foregaroundColor: Color("onErrorColor")),
                show: $showError)
            case is CreateAddressState.StateError : VStack(spacing:0){
                let errorState = createAddressState.state as? CreateAddressState.StateError
                switch(errorState?.throwable){
                case is NoSelectedCityUuidException :  ErrorView(
                    mainText: "Уупс, кажется, у вас не выбран город",
                    extratext: "Зайдите в настройки, расположенные в профиле, там можно выбрать город",
                    action: {
                        viewModel.viewModel.getStreetList()
                    })
                case is NoUserUuidException :  ErrorView(
                    mainText: "Уупс, кажется, у вас проблемы с авторизацией",
                    extratext: "Зайдите в настройки, расположенные в профиле, там можной выйти и авторизоваться снова",
                    action: {
                        viewModel.viewModel.getStreetList()
                    })
                case is NoStreetByNameAndCityUuidException : ErrorView(
                    mainText: "Ууупс, кажется мы не смогли добавить адрес",
                    extratext: "Попробуйте добавить адрес еще раз",
                    action: {
                        viewModel.viewModel.getStreetList()
                    }
                )
                default:
                    ErrorView(
                        mainText: "Не удается загрузить данные",
                        extratext: "Проверьте соединение и повторите попытку",
                        action: {
                            viewModel.viewModel.getStreetList()
                        })
                }
                
            }
            default : EmptyView()
            }
        }
        .hiddenNavigationBarStyle()
        .background(Color("background"))
        .onAppear(){
            viewModel.viewModel.getStreetList()
            listener = viewModel.viewModel.streetListState.watch { createAddressStateVM in
                if(createAddressStateVM != nil ){
                    createAddressState = createAddressStateVM!
                    hasStreetError = createAddressState.hasStreetError
                    hasHouseError = createAddressState.houseFieldError  == CreateAddressState.FieldError.incorrect
                    hasFlatError = createAddressState.hasFlatError
                    hasEntaranceError = createAddressState.hasEntranceError
                    hasFloorError = createAddressState.hasFloorError
                    hasCommentError = createAddressState.hasCommentError
                    filteredList = createAddressState.suggestedStreetList.map({ street in
                        StreetItem(
                            id: street.uuid,
                            name: street.name
                        )
                    })
                }
                
                createAddressState.eventList.forEach { event in
                    switch(event){
                    case is CreateAddressStateEventAddressCreatedSuccess : self.presentationMode.wrappedValue.dismiss()
                        show = true
                    case is CreateAddressStateEventAddressCreatedFailed: showError = true
                    default:
                        print("def")
                    }
                }
                
                if !createAddressState.eventList.isEmpty{
                    viewModel.viewModel.consumeEventList(eventList: createAddressState.eventList)
                }
            }
        }.onDisappear(){
            listener?.close()
            listener = nil
        }
    }
}
