//
//  ChangeCityView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct ChangeCityView: View {
    
    let cities : [ChangeCityItem]
    
    init() {
        cities = [ChangeCityItem(city: "Kimry", isSelected:true), ChangeCityItem(city: "Dubna", isSelected:false)]
    }
    
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_SELECT_CITY_CITY,  cost: "", count: "", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            ScrollView {
                LazyVStack{
                    ForEach(cities){ city in
                        Button(action:{
                            //change city
                            self.presentationMode.wrappedValue.dismiss()
                        }
                        ){
                            ChangeCityItemView(city: city).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
        }
        .background(Color("background"))
        .navigationBarHidden(true)
        
    }
}

struct ChangeCityView_Previews: PreviewProvider {
    static var previews: some View {
        ChangeCityView()
    }
}
