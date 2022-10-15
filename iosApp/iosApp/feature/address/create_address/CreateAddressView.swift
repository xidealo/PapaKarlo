//
//  CreateAddressView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI

struct CreateAddressView: View {
    
    @State var street:String = ""
    @State var house:String = ""
    @State var flat:String = ""
    @State var entarance:String = ""
    @State var floor:String = ""
    @State var comment:String = ""
    
    @Binding var show:Bool
    
    @State var showError:Bool = false
    
    @ObservedObject private var viewModel = CreateAddressViewModel()
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        ZStack(alignment: .bottom){
            VStack{
                ToolbarView(title: Strings.TITLE_CREATION_ADDRESS, cost: "220 R", count: "2",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
                
                ScrollView{
                    VStack(spacing:0){
                        VStack{
                            SearchEditTextView(
                                hint: Strings.HINT_CREATION_ADDRESS_STREET,
                                text: $street,
                                limit: 100,
                                list: $viewModel.createAddressViewState.streetList,
                                hasError: $viewModel.createAddressViewState.hasStreetError,
                                errorMessage: "Выберите улицу из списка"
                            )
                            .padding(.top, Diems.MEDIUM_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            
                            Group{
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_HOUSE,
                                    text: $house,
                                    limit: 100,
                                    hasError: $viewModel.createAddressViewState.hasHouseError,
                                    errorMessage: "Введите номер дома"
                                )
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_FLAT,
                                    text: $flat,
                                    limit: 5,
                                    hasError: .constant(false))
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE,
                                    text: $entarance,
                                    limit: 5,
                                    hasError: .constant(false))
                                EditTextView(
                                    hint: Strings.HINT_CREATION_ADDRESS_FLOOR,
                                    text: $floor,
                                    limit: 5,
                                    hasError: .constant(false))
                            }.padding(.horizontal, Diems.MEDIUM_PADDING)
                            
                            EditTextView(
                                hint: Strings.HINT_CREATION_ADDRESS_COMMENT,
                                text: $comment,
                                limit: 5,
                                hasError: .constant(false))
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.bottom, Diems.SMALL_PADDING)
                        }
                    }
                }
                
                Button(action: {
                    viewModel.onCreateAddressClicked(streetName: street, house: house, flat: flat, entrance: entarance, floor: floor, comment: comment){ isBack in
                        if(isBack){
                            DispatchQueue.main.async {
                                presentationMode.wrappedValue.dismiss()
                                show = true
                            }
                        }else{
                            showError = !isBack
                        }
                    }
                }) {
                    Text(Strings.ACTION_CREATION_ADDRESS_ADD).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }.padding(Diems.MEDIUM_PADDING)
            }
            .navigationBarHidden(true)
            .background(Color("background"))
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Что-то пошло не так"),
                    show: $showError,
                    backgroundColor: Color("errorColor"),
                    foregaroundColor: Color("onErrorColor")),
                show: $showError)
        }.ignoresSafeArea(.keyboard)
    }
}

struct CreateAddressView_Previews: PreviewProvider {
    static var previews: some View {
        CreateAddressView(show: .constant(true))
    }
}
