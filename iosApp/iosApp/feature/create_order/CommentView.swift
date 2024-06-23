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
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        VStack{
//            EditTextView(
//                hint: Strings.HINT_CREATE_COMMENT_COMMENT,
//                text:$createOrderViewModel.creationOrderViewState.comment,
//                limit: 255,
//                hasError: .constant(false)
//            )
            Spacer()
            
            Button {
                self.mode.wrappedValue.dismiss()
            } label: {
                Text(Strings.ACTION_CREATE_COMMENT_SAVE)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(AppColor.surface)
                    .background(AppColor.primary)
                    .cornerRadius(Diems.BUTTON_RADIUS)
            }
        }
        .padding(Diems.MEDIUM_PADDING)
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
    }
}
