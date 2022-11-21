//
//  ConfirmView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI
import Combine

struct ConfirmView: View {
    
    @State private var code:String = ""
    
    @ObservedObject private var viewModel : ConfirmViewModel
    private let phone:String
    @Binding var rootIsActive:Bool

    init(auth:AuthManager, phone:String, rootIsActive: Binding<Bool>){
        viewModel = ConfirmViewModel(auth: auth)
        self.phone = phone
        self._rootIsActive = rootIsActive
    }
    
    var body: some View {
        VStack(spacing:0){
            switch(viewModel.confirmViewState.confirmState){
            case ConfirmState.loading: LoadingView()
            case ConfirmState.success: ConfirmViewSuccessView(code: $code, viewModel: viewModel, phone: phone)
            default : ConfirmViewSuccessView(code: $code, viewModel: viewModel, phone: phone)
            }
        }
        .onReceive(viewModel.$confirmViewState, perform: { confirmViewState in
            confirmViewState.actionList.forEach { action in
                print("BEFORE ROOT IS ACTIVER \(rootIsActive)")

                switch(action){
                case ConfirmAction.back : self.rootIsActive = false
                }
            }
            print("ROOT IS ACTIVER \(rootIsActive)")
            
            if !confirmViewState.actionList.isEmpty{
                viewModel.clearActions()
            }
        })
     
    }
}

struct ConfirmViewSuccessView: View {
    @Binding var code:String
    @ObservedObject var viewModel : ConfirmViewModel
    @State var show:Bool = false
    
    @State private var timeRemaining = 60
    @State private var isEnabled = false
    
    let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
    let phone:String
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        
        VStack(spacing:0){
            ToolbarView(
                title: "",
                cost: "",
                count: "",
                isCartVisible: false,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
                        
            VStack(spacing:0){
                
                Spacer()

                Text(Strings.MSG_CONFIRM_ENTER_CODE + phone)
                    .multilineTextAlignment(.center)
                    .foregroundColor(Color("onSurface"))
                    .padding(.bottom, Diems.MEDIUM_PADDING)

                //SmsTextField(count: 6)
                
                EditTextView(
                    hint: Strings.HINT_CONFIRM_CODE,
                    text:$code,
                    limit: 6,
                    keyBoadrType: UIKeyboardType.numberPad,
                    hasError: .constant(false)
                )
                .onReceive(Just(code)) { _ in
                    if(code.count == 6){
                        viewModel.checkCode(code: code)
                        code = ""
                    }
                }
                
                Spacer()
                
                Button(
                    action: {
                        isEnabled = false
                        timeRemaining = 60
                    }
                ){
                    if(isEnabled){
                        Text(Strings.ACTION_CONFIRM_GET_CODE)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .foregroundColor(Color("surface"))
                            .background(Color("primary"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                        
                    }
                    else{
                        Text("Запросить код повторно \(timeRemaining) сек.")
                            .frame(maxWidth: .infinity)
                            .padding()
                            .foregroundColor(Color("surface"))
                            .background(Color("onPrimaryDisabled"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                            .multilineTextAlignment(.center)
                    }
                    
                }.disabled(!isEnabled)
            }
            .padding(Diems.MEDIUM_PADDING)
        }
        .background(Color("surface"))
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Неправильный код"),
                show: $show,
                backgroundColor:Color("errorColor"),
                foregaroundColor: Color("onErrorColor")
            ), show: $show)
        .onReceive(timer){ time in
            if timeRemaining > 0{
                timeRemaining -= 1
            }
            
            if(timeRemaining == 0){
                isEnabled = true
            }
        }
        .onReceive(viewModel.$confirmViewState) { confirmViewState in
            if(confirmViewState.confirmState == ConfirmState.error){
                self.show = true
            }
        }
        .hiddenNavigationBarStyle()
    }
}
