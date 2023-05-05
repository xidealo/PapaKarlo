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
        HStack(spacing:0){
            VStack(spacing:0){
                Text(placeHolder)
                    .labelSmall(weight: .medium)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .frame(maxWidth:.infinity, alignment: .leading)
                
                Text(text)
                    .bodyMedium()
                    .frame(maxWidth:.infinity, alignment: .leading)
                    .foregroundColor(AppColor.onSurface)
                    .multilineTextAlignment(.leading)
            }
            .frame(maxWidth:.infinity, alignment: .leading)
            Image(systemName:"chevron.right")
                .foregroundColor(AppColor.onSurfaceVariant)
        }
        .padding(.horizontal, Diems.MEDIUM_PADDING)
        .padding(.vertical, Diems.SMALL_PADDING)
        .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
        }
        
    }
}

struct NavigationTextCard_Previews: PreviewProvider {
    static var previews: some View {
        NavigationTextCard(placeHolder: "placeholder", text: "SKADASLDAL< :LDfd;lfsd;l,f", destination:ChangeCityView())
    }
}
