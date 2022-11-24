//
//  CommentView.swift
//  iosApp
//
//  Created by Марк Шавловский on 31.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CommentView: View {
    //TODO(Make 
    @ObservedObject var createOrderViewModel:CreateOrderViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack{
            EditTextView(
                hint: Strings.HINT_CREATE_COMMENT_COMMENT,
                text:$createOrderViewModel.creationOrderViewState.comment,
                limit: 255,
                hasError: .constant(false)
            )
            Spacer()
            
            Button {
                self.mode.wrappedValue.dismiss()
            } label: {
                Text(Strings.ACTION_CREATE_COMMENT_SAVE)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }
        }
        .padding(Diems.MEDIUM_PADDING)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
    }
}

struct CommentView_Previews: PreviewProvider {
    static var previews: some View {
        CommentView(createOrderViewModel: CreateOrderViewModel())
    }
}
