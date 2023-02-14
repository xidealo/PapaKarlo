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
    
    @Binding var show:Bool
    
    @State var showError:Bool = false
    
    @StateObject var viewModel:CreateAddressHolder = CreateAddressHolder()

    @State var listener: Closeable? = nil
    
    @State var createAddressState = CreateAddressState(
        streetItemList: [],
        state: CreateAddressState.StateLoading(),
        hasStreetError: false,
        hasHouseError: nil,
        hasFlatError: false,
        hasEntranceError: false,
        hasFloorError: false,
        hasCommentError: false,
        isCreateLoading: false,
        eventList: []
    )
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        ZStack(alignment: .bottom){
            switch(createAddressState.state){
            case CreateAddressState.StateLoading(): LoadingView()
            case CreateAddressState.StateSuccess(): VStack(spacing:0){
                ToolbarView(
                    title: Strings.TITLE_CREATION_ADDRESS,
                    back: {
                        self.presentationMode.wrappedValue.dismiss()
                    }
                )
                
                ScrollView{
                    VStack(spacing:0){
                        VStack(spacing:0){
                            SearchEditTextView(
                                hint: Strings.HINT_CREATION_ADDRESS_STREET,
                                text: $street,
                                limit: 100,
                                list: createAddressState.streetItemList.map({ street in
                                    StreetItem(
                                        id: street.uuid,
                                        name: street.name
                                    )
                                }),
                                hasError: createAddressState.hasStreetError,
                                errorMessage: "Выберите улицу из списка"
                            )
                            .padding(.top, Diems.MEDIUM_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            
                            Group{
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_HOUSE,
                                    text: $house,
                                    limit: 5,
                                    hasError: .constant(false),
                                    hasErrorState: (createAddressState.hasHouseError == CreateAddressState.FieldError.incorrect),
                                    errorMessage: "Введите номер дома"
                                )
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_FLAT,
                                    text: $flat,
                                    limit: 5,
                                    hasError: .constant(false),
                                    hasErrorState: createAddressState.hasFlatError,
                                    errorMessage: "Максимальная длина поля 5"
                                )
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE,
                                    text: $entarance,
                                    limit: 5,
                                    hasError: .constant(false),
                                    hasErrorState: createAddressState.hasEntranceError,
                                    errorMessage: "Максимальная длина поля 5"
                                )
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_FLOOR,
                                    text: $floor,
                                    limit: 5,
                                    hasError: .constant(false),
                                    hasErrorState: createAddressState.hasFloorError,
                                    errorMessage: "Максимальная длина поля 5"
                                )
                            }
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.SMALL_PADDING)
                            
                            
                            EditTextView(
                                hint: Strings.HINT_CREATION_ADDRESS_COMMENT,
                                text: $comment,
                                limit: 100,
                                hasError: .constant(false),
                                hasErrorState: createAddressState.hasCommentError,
                                errorMessage: "Максимальная длина поля 100")
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.vertical, Diems.SMALL_PADDING)
                        }
                    }
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
                        Text(Strings.ACTION_CREATION_ADDRESS_ADD).frame(maxWidth: .infinity)
                            .padding()
                            .foregroundColor(Color("surface"))
                            .background(Color("primary"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                    }
                }.padding(Diems.MEDIUM_PADDING)
            }
            .hiddenNavigationBarStyle()
            .background(Color("background"))
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Что-то пошло не так"),
                    show: $showError,
                    backgroundColor: Color("errorColor"),
                    foregaroundColor: Color("onErrorColor")),
                show: $showError)
                
            default : EmptyView()
            }
            
        }
        .onAppear(){
            viewModel.viewModel.getStreetList()
            listener = viewModel.viewModel.streetListState.watch { createAddressStateVM in
                if(createAddressStateVM != nil ){
                    print(createAddressStateVM)
                    createAddressState = createAddressStateVM!
                }
            }
        }.onDisappear(){
            listener?.close()
            listener = nil
        }.ignoresSafeArea(.keyboard)
    }
}
