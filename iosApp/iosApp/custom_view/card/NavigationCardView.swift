//
//  NavigationCardView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct NavigationCardView<Content: View>: View {
    
    let icon:String?
    
    let label:String
    let destination:Content
    var isSystem = true
    
    var body: some View {
            NavigationLink(
                destination:destination
            ){
                HStack(spacing:0){
                    if icon != nil{
                        if(isSystem){
                            Image(
                                systemName: icon ?? "")
                            .resizable()
                            .renderingMode(.template)
                            .frame(width: 24, height: 24)
                            .foregroundColor(Color("onSurfaceVariant"))
                        }else{
                            IconImage(
                                width: 24,
                                height: 24,
                                imageName: icon ?? ""
                            )
                            .foregroundColor(Color("onSurfaceVariant"))
                        }
                    }
                    if(icon == nil){
                        Text(label)
                            .frame(
                                maxWidth: .infinity, alignment: .leading).foregroundColor(Color("onSurface")
                            )
                    }else{
                        Text(label)
                            .frame(
                                maxWidth: .infinity, alignment: .leading).foregroundColor(Color("onSurface")
                            )
                            .padding(
                                .leading, Diems.MEDIUM_PADDING
                            )
                    }
                  
                    Image(systemName:"chevron.right").foregroundColor(Color("onSurfaceVariant"))
                }.frame(maxWidth:.infinity)
                .padding(Diems.MEDIUM_PADDING)
                .background(Color("surface"))
                .cornerRadius(Diems.MEDIUM_RADIUS)
            }
    }
}

struct NavigationCardView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationCardView(icon: "person", label: "О приложении", destination: SplashView())
    }
}
