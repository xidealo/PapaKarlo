//
//  BottomBarView.swift
//  iosApp
//
//  Created by Марк Шавловский on 30.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI


//class BottomBarStore{
//    var bottomBarView : BottomBarView? = nil
//
//    func getBottomBar() -> BottomBarView{
//
//        if(bottomBarView == nil){
//            let newBottomBarView = BottomBarView()
//            bottomBarView = newBottomBarView
//            return newBottomBarView
//        }
//
//        return bottomBarView!
//    }
//}

struct BottomBarView: View {
    
    @State var isSelected:Int
    
    @State var isGoToCafeList = false
    @State var isGoToMenu = false
    @State var isGoToPropfile = false

    var body: some View {
        HStack{
            Button {
                isGoToCafeList = true
                isGoToMenu = false
                isGoToPropfile = false
            } label: {
                NavigationLink(
                    destination:CafeListView(),
                    isActive: $isGoToCafeList
                ){
                    EmptyView()
                }
                
                if(isSelected == 0){
                    Text("Кафе").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                }else{
                    Text("Кафе").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                }
            }
            Button {
                isGoToMenu = true
                isGoToCafeList = false
                isGoToPropfile = false
            } label: {
                NavigationLink(
                    destination:MenuView(),
                    isActive: $isGoToMenu
                ){
                    EmptyView()
                }
                if(isSelected == 1){
                    Text("Меню").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                }else{
                    Text("Меню").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                }
            }
            Button {
                isGoToMenu = false
                isGoToCafeList = false
                isGoToPropfile = true
            } label: {
                NavigationLink(
                    destination:ProfileView(),
                    isActive: $isGoToPropfile
                ){
                    EmptyView()
                }
                
                if(isSelected == 2){
                    Text("Профиль").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                }else{
                    Text("Профиль").frame(maxWidth:.infinity).padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                }
            }
        }.background(Color("surface"))
    }
}

struct BottomBarView_Previews: PreviewProvider {
    static var previews: some View {
        BottomBarView(isSelected: 1)
    }
}
