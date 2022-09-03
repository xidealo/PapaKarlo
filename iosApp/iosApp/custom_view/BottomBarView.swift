//
//  BottomBarView.swift
//  iosApp
//
//  Created by Марк Шавловский on 30.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BottomBarView: View {
    
    @State var isSelected:Int
    let iconSize = CGFloat(22)
    let iconBlockHeight = CGFloat(24)
    let iconTopPaddig = CGFloat(8)

    var body: some View {
        HStack{
            NavigationLink(
                destination:CafeListView()
            ){
                VStack{
                    if(isSelected == 0){
                        ZStack{
                            Image(systemName:"mappin").font(.system(size:iconSize)).foregroundColor(Color("primary")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        
                        Text("Кафе").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                    }else{
                        ZStack{
                            Image(systemName:"mappin").font(.system(size: iconSize)).foregroundColor(Color("onSurfaceVariant")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        Text("Кафе").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                    }
                }
            }
            NavigationLink(
                destination:MenuView()
            ){
                VStack{
                    if(isSelected == 1){
                        ZStack{
                            Image(systemName:"list.dash").font(.system(size: iconSize)).foregroundColor(Color("primary")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        Text("Меню").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                    }else{
                        ZStack{
                            Image(systemName:"list.dash").font(.system(size: iconSize)).foregroundColor(Color("onSurfaceVariant")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        Text("Меню").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                    }
                }
            }
            
            NavigationLink(
                destination:ProfileView()
            ){
                VStack{
                    if(isSelected == 2){
                        ZStack{
                            Image(systemName: "person.crop.circle").font(.system(size: iconSize)).foregroundColor(Color("primary")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        Text("Профиль").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("primary"))
                    }else{
                        ZStack{
                            Image(systemName: "person.crop.circle").font(.system(size: iconSize)).foregroundColor(Color("onSurfaceVariant")).padding(.top, iconTopPaddig)
                        }.frame(height: iconBlockHeight)
                        Text("Профиль").frame(maxWidth:.infinity).padding(.horizontal, Diems.MEDIUM_PADDING).foregroundColor(Color("onSurfaceVariant"))
                    }
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
