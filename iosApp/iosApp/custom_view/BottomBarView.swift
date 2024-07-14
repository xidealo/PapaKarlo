//
//  BottomBarView.swift
//  iosApp
//
//  Created by Марк Шавловский on 30.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct BottomBarView: View {
    
    @Binding var selection: MainContainerState
    @Binding var title: LocalizedStringKey

    let iconSize = CGFloat(22)
    let iconBlockHeight = CGFloat(24)
    let iconTopPaddig = CGFloat(8)
    
    var body: some View {
        HStack(spacing:0){
            Button {
                selection = .cafeList
                title = "title_restaurants"
            } label: {
                VStack(spacing:0){
                    if(selection == .cafeList){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "CafesIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("title_restaurants")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "CafesIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("title_restaurants")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }

            Button {
                selection = .menu
                title = "title_menu"
            } label: {
                VStack(spacing:0){
                    if(selection == .menu){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "MenuIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("title_menu")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "MenuIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("title_menu")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }
            
            Button {
                selection = .profile
                title = "title_profile"
            } label: {
                VStack(spacing:0){
                    if(selection == .profile){
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "ProfileIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.primary)
                        }.frame(height: iconBlockHeight)
                        Text("title_profile")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.primary)
                    }else{
                        ZStack{
                            IconImage(width: 24, height: 24, imageName: "ProfileIcon")
                                .padding(.top, iconTopPaddig)
                                .foregroundColor(AppColor.onSurfaceVariant)
                        }.frame(height: iconBlockHeight)
                        Text("title_profile")
                            .labelMedium(weight: .medium)
                            .frame(maxWidth:.infinity)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, 4)
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }.padding(Diems.HALF_SMALL_PADDING)
            }
          
        }.background(AppColor.surface)
    }
}
