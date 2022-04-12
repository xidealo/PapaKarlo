//
//  CafeListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

struct CafeListView: View {
    
    let cafes:[CafeItem]
    
    init() {
        cafes = [CafeItem(id: UUID(), address: "Kimry chapaevo 22a", workingHours: "9:00 - 22:00", isOpenMessage: "Open", isOpenColor: Color.green
        ), CafeItem(id: UUID(), address: "Kimry chapaevo 22a", workingHours: "9:00 - 22:00", isOpenMessage: "Open", isOpenColor: Color.green
        )]
    }
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_CAFE_LIST, cost: "220 R", count: "2",  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            ScrollView {
                LazyVStack{
                    ForEach(cafes){ cafe in
                        CafeItemView(cafeItem: cafe)
                            .padding(.bottom, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
        }.background(Color("background"))
        .navigationBarHidden(true)

        
    }
}

struct CafeListView_Previews: PreviewProvider {
    static var previews: some View {
        CafeListView()
    }
}
