//
//  ProductDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

extension String {
    
    func load() -> UIImage {
        do {
            guard let url = URL(string: self) else {
                return UIImage()
            }
            let data:Data = try Data(contentsOf: url)
            return UIImage(data: data) ?? UIImage()
        }catch {
            return UIImage()
        }
    }
}

struct ProductDetailsView: View {
    private let menuProductUI:MenuProductUI
    
    let menuProductUuid:String
    
    init(menuProductUuid:String) {
        self.menuProductUuid = menuProductUuid
        
        menuProductUI = MenuProductUI(name:  "Бургер", size: "200 г", oldPrice: "250 P", newPrice: "200 Р", description: "Сочный пурге, с кртшка, с вкусный сыр, с вкусное мясцо", imageLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg")
    }
    
    var body: some View {
        VStack{
            
            VStack{
                Image(uiImage: menuProductUI.imageLink.load())
                    .resizable()
                    .scaledToFit()
                    .frame(maxWidth: .infinity)
                
                Group{
                    HStack{
                        Text(menuProductUI.name).frame(maxWidth: .infinity, alignment: .leading).font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
                        Text(menuProductUI.size).font(.system(size: Diems.SMALL_TEXT_SIZE, weight: .thin, design: .default))
                    }.padding(.top, Diems.MEDIUM_PADDING)
                    
                    HStack{
                        if menuProductUI.oldPrice != nil{
                            StrikeText(text: menuProductUI.oldPrice ?? "")
                        }
                        Text(menuProductUI.newPrice)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default))
                    }.frame(maxWidth: .infinity, alignment: .leading).padding(.top, Diems.SMALL_PADDING)
                    
                    Text(menuProductUI.description).frame(maxWidth: .infinity, alignment: .leading).padding(.top, Diems.LARGE_PADDING).padding(.bottom, Diems.MEDIUM_PADDING)
                    
                }.padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .padding(Diems.MEDIUM_PADDING)

            Spacer()
            
            Button(action: {
                print("button pressed")
            }) {
                Text(Strings.ACTION_PRODUCT_DETAILS_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .navigationTitle(
            Text(menuProductUI.name)
        )
    }
}

struct ProductDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        ProductDetailsView(menuProductUuid: "")
    }
}
