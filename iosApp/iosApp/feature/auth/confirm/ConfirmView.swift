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
    
    init(auth:AuthManager, phone:String, isGoToProfile:Bool){
        viewModel = ConfirmViewModel(auth: auth, isGoToProfile: isGoToProfile)
        self.phone = phone
    }
    
    var body: some View {
        switch(viewModel.confirmViewState.confirmState){
        case ConfirmState.loading: LoadingView()
        case ConfirmState.success: ConfirmViewSuccessView(code: $code, viewModel: viewModel, phone: phone)
        case ConfirmState.goToProfile : NavigationLink(
            destination:ProfileView(show: false),
            isActive: .constant(true)
        ){
            EmptyView()
        }
        case ConfirmState.goToCreateOrder: NavigationLink(
            destination:CreateOrderView(),
            isActive: .constant(true)
        ){
            EmptyView()
        }
        default : ConfirmViewSuccessView(code: $code, viewModel: viewModel, phone: phone)
        }
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
    
    var body: some View {
        
        VStack{
            ToolbarView(title: "", cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            Spacer()
            Text(Strings.MSG_CONFIRM_ENTER_CODE + phone).multilineTextAlignment(.center)
            
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
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Неправильный код"),
                show: $show,
                backgroundColor:Color("errorColor"),
                foregaroundColor: Color("onErrorColor")
            ), show: $show)
        .padding(Diems.MEDIUM_PADDING)
        .onReceive(timer){ time in
            if timeRemaining > 0{
                timeRemaining -= 1
            }
            
            if(timeRemaining == 0){
                isEnabled = true
            }
        }
        .navigationBarHidden(true)
        .onReceive(viewModel.$confirmViewState) { confirmViewState in
            if(confirmViewState.confirmState == ConfirmState.error){
                self.show = true
            }
        }
    }
}

struct ConfirmView_Previews: PreviewProvider {
    static var previews: some View {
        ConfirmView(auth: AuthManager(), phone: "+79969224186", isGoToProfile: true)
    }
}
