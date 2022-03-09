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
    let menuProductUI:MenuProductUI
    var body: some View {
        VStack{
            
            VStack{
                    Image(uiImage: menuProductUI.imageLink.load())
                        .resizable()
                        .scaledToFit()
                        .frame(maxWidth: .infinity)

                    HStack{
                        
                    }
                    HStack{
                        
                    }
                    
                    Text(menuProductUI.description)
            }
            .background(Color("surface"))
            .padding(Diems.MEDIUM_PADDING)
            .cornerRadius(20.0)


           
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
        ProductDetailsView(menuProductUI:MenuProductUI(name:  "Бургер", size: "200 г", oldPrice: nil, newPrice: "200", description: "Сочный пургер", imageLink: "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg"))
    }
}
