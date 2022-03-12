//
//  NavigationCardView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct NavigationCardView<Content: View>: View {
    
    let icon:String
    let label:String
    let destination:Content
    
    var body: some View {
            NavigationLink(
                destination:destination
            ){
                HStack{
                    Image(systemName: icon)
                    Text(label).frame(maxWidth:.infinity, alignment: .leading).foregroundColor(Color("onSurface"))
                    Image(systemName:"chevron.right")
                }.frame(maxWidth:.infinity)
                .padding(Diems.MEDIUM_PADDING)
                .background(Color("surface"))
                .cornerRadius(Diems.MEDIUM_RADIUS)
                .shadow(radius: 1)
            }
    }
}

struct NavigationCardView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationCardView(icon: "person", label: "О приложении", destination: SplashView())
    }
}
