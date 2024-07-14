//
//  NavigationCardView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct NavigationCardView<Content: View>: View {
    
    let icon : String?
    
    let label : String
    let destination : Content
    var isSystem = true
    
    var body: some View {
        NavigationLink(
            destination:destination
        ){
            HStack(spacing:0){
                if let notNullIcon = icon {
                    if(isSystem){
                        Image(
                            systemName: notNullIcon)
                        .resizable()
                        .renderingMode(.template)
                        .frame(width: 24, height: 24)
                        .foregroundColor(AppColor.onSurfaceVariant)
                    }else{
                        IconImage(
                            width: 24,
                            height: 24,
                            imageName: notNullIcon
                        )
                        .foregroundColor(AppColor.onSurfaceVariant)
                    }
                }
                if(icon == nil){
                    Text(label)
                        .bodyLarge()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurface)
                }else{
                    Text(label)
                        .bodyLarge()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurface)
                        .padding(.leading, Diems.MEDIUM_PADDING)
                }
                Image(systemName:"chevron.right")
                    .foregroundColor(AppColor.onSurfaceVariant)
                
            }.frame(maxWidth:.infinity)
                .padding(12)
                .background(AppColor.surface)
                .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}

struct NavigationCardView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationCardView(icon: "person", label: "О приложении", destination: SplashView())
    }
}
