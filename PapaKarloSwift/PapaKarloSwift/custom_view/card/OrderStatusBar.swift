//
//  OrderStatusBar.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.03.2022.
//

import SwiftUI

struct OrderStatusBar: View {
    var body: some View {
        HStack{
            ForEach(1...5, id: \.self){ i in
               
                //StatusChip(status: "готов")
                FutureStep()
            }
        }
        
    }
}

struct DoneStep: View {
    let status:String
    
    var body: some View {
        HStack{
            ForEach(1...5, id: \.self){ i in
               
                StatusChip(status: "готов")
            }
        }
        
    }
}

struct FutureStep: View {
    var body: some View {
        RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS).fill(Color("surfaceVariant")).frame(maxWidth:.infinity, maxHeight:30)
        
    }
}

struct OrderStatusBar_Previews: PreviewProvider {
    static var previews: some View {
        OrderStatusBar()
    }
}
