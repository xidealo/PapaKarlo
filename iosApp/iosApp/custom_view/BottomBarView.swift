//
//  BottomBarView.swift
//  iosApp
//
//  Created by Марк Шавловский on 30.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BottomBarView: View {
    
    @Binding var selection:Int
    @Binding var title:LocalizedStringKey

    let iconSize = CGFloat(22)
    let iconBlockHeight = CGFloat(24)
    let iconTopPaddig = CGFloat(8)
    
    var body: some View {
        HStack(spacing:0){
            Button {
                selection = 0
                title = "titleCafeList"
            } label: {
                VStack(spacing:0){
                    if(selection == 0){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "CafesIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("Кафе")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "CafesIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("Кафе")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }

            Button {
                selection = 1
                title = "titleMenu"
            } label: {
                VStack(spacing:0){
                    if(selection == 1){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "MenuIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("Меню")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "MenuIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("Меню")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }
            
            Button {
                selection = 2
                title = "titleProfile"
            } label: {
                VStack(spacing:0){
                    if(selection == 2){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "ProfileIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("Профиль")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "ProfileIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("Профиль")
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 2)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }
          
        }.background(AppColor.surface)
    }
}
