//
//  LoginView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI
import Combine
import shared

struct LoginView: View {

    @Binding var rootIsActive:Bool
    @Binding var isGoToCreateOrder:Bool
    @State var goToConfirm:Bool = false
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @State var showSomethigWrongError:Bool = false
    @State var showTooManyRequestsError:Bool = false

    @State var viewModel: LoginViewModel = LoginViewModel(
        requestCode: iosComponent.provideRequestCodeUseCase(),
        formatPhoneNumber: iosComponent.provideFormatPhoneNumberUseCase(),
        getPhoneNumberCursorPosition: iosComponent.provideGetPhoneNumberCursorPositionUseCase(),
        checkPhoneNumber: iosComponent.provideCheckPhoneNumberUseCase()
    )

    //State
    @State var isLoading: Bool = false
    @State var hasPhoneError: Bool = false
    @State var phone = ""
    @State var phoneNumberCursorPosition = Int32(shared.Constants().PHONE_CODE.count)
    // ---

    @State var stateListener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    
    var body: some View {
        VStack(spacing:0){
            NavigationLink(
                destination:ConfirmView(
                    phone: phone,
                    rootIsActive: self.$rootIsActive,
                    isGoToCreateOrder: $isGoToCreateOrder
                ),
                isActive: $goToConfirm
            ){
                EmptyView()
            }
            if(isLoading){
                LoadingView()
            }else{
                LoginViewSuccessView(
                    phone: $phone,
                    hasError: $hasPhoneError,
                    action: viewModel.handleAction
                )
            }
        }
        .onAppear(){
            viewModel.handleAction(action: LoginActionInit())
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Что-то пошло не так"),
                show: $showSomethigWrongError,
                backgroundColor:AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showSomethigWrongError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Превышен лимит на отправку сообщений"),
                show: $showTooManyRequestsError,
                backgroundColor:AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showTooManyRequestsError
        )
    }

    func subscribe(){
        stateListener = viewModel.state.watch {  loginStateVM in
            if let loginState = loginStateVM {
                print(loginState)
                phone = loginState.phoneNumber
                hasPhoneError = loginState.hasPhoneError
                isLoading = loginState.isLoading
                phoneNumberCursorPosition = loginState.phoneNumberCursorPosition
            }

        }
    }

    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let loginEvents = events as? [LoginEvent] ?? []

                loginEvents.forEach { event in
                    print(event)
                    switch(event){
                    case is LoginEventNavigateBack : self.mode.wrappedValue.dismiss()
                    case is LoginEventNavigateToConfirm : goToConfirm = true
                    case is LoginEventShowTooManyRequestsError: showTooManyRequestsError = true
                    case is LoginEventShowSomethingWentWrongError: showSomethigWrongError = true
                    default:
                        print("def")
                    }
                }

                if !loginEvents.isEmpty {
                    viewModel.consumeEvents(events: loginEvents)
                }
            }
        })
    }

    func unsubscribe(){
        stateListener?.close()
        stateListener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct LoginViewSuccessView: View {
    @Binding var phone:String
    @Binding var hasError:Bool
    @State var isSelected:Bool = false

    let action: (LoginAction) -> Void

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title:"",
                back: {
                    action(LoginActionBackClick())
                }
            )
            
            VStack(spacing:0){
                Spacer()
                Image("LoginLogo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(height: 156)
                
                Text("titleLoginEnterPhone")
                    .bodyLarge()
                    .multilineTextAlignment(.center)
                    .foregroundColor(AppColor.onSurface)
                    .padding(.top, Diems.MEDIUM_PADDING)
                
                EditTextView(
                    hint: Strings.HINT_LOGIN_PHONE,
                    text: $phone,
                    limit: 18,
                    keyBoadrType: UIKeyboardType.phonePad,
                    hasError: $hasError,
                    errorMessage: "Введите корректный номер телефона",
                    textChanged: { str in
                        action(LoginActionChangePhoneNumber(phoneNumber: str, cursorPosition: 0))
                    }
                )
                .padding(.top, 16)
                .onReceive(Just(phone)) { _ in
                    minCode()
                }
                .keyboardType(.phonePad)
                
                Spacer()
                
                Button {
                    action(LoginActionNextClick())
                } label: {
                    ButtonText(text: Strings.ACTION_LOGIN_LOGIN)
                }
            }.padding(Diems.MEDIUM_PADDING)
        }
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
    }
    
    func minCode() {
        if phone.count < 2 {
            phone = String("+7")
        }
    }
}
