//
//  NavigationTextCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct NavigationTextCard<Content: View>: View {
    
    let placeHolder:String
    let text:String
    let destination:Content

    var body: some View {
        NavigationLink(
            destination:destination
        ){
        HStack{
            VStack{
                PlaceholderText(text:placeHolder)
                Text(text).frame(maxWidth:.infinity, alignment: .leading)
                
            }.frame(maxWidth:.infinity, alignment: .leading)
            Image(systemName:"chevron.right").foregroundColor(Color("onSurfaceVariant"))
        }.padding(.horizontal, Diems.MEDIUM_PADDING).padding(.vertical, Diems.SMALL_PADDING).background(Color("surface")).cornerRadius(Diems.MEDIUM_RADIUS)
        }
        
    }
}

struct NavigationTextCard_Previews: PreviewProvider {
    static var previews: some View {
        NavigationTextCard(placeHolder: "placeholder", text: "text", destination:ChangeCityView())
    }
}
