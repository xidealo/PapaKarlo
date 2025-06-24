//
//  ToastView.swift
//  iosApp
//
//  Created by Марк Шавловский on 17.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct Toast {
    let title: String
}

struct ToastView: View {
    let toast: Toast
    @Binding var show: Bool
    let backgroundColor: Color
    let foregroundColor: Color
    
    var paddingBottom: CGFloat = 0
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            Text(toast.title)
                .labelLarge()
                .padding(.vertical, 12)
                .padding(.horizontal, 16)
                .frame(maxWidth: .infinity, maxHeight: 48, alignment: .leading)
                .foregroundColor(foregroundColor)
                .background(backgroundColor)
                .cornerRadius(Diems.MEDIUM_RADIUS)
                .padding(16)
                .padding(.bottom, paddingBottom)
        }
        .frame(maxWidth: .infinity)
        .transition(AnyTransition.move(edge: .bottom).combined(with: .opacity))
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                if self.show {
                    self.show = false
                }
            }
        }
    }
}

struct Overlay<T: View>: ViewModifier {
    @Binding var show: Bool
    let overlayView: T

    func body(content: Content) -> some View {
        ZStack {
            content
            if show {
                overlayView
            }
        }
    }
}

extension View {
    func overlay<T: View>(overlayView: T, show: Binding<Bool>) -> some View {
        modifier(Overlay(show: show, overlayView: overlayView))
    }
}

struct ToastView_Previews: PreviewProvider {
    static var previews: some View {
        ToastView(
            toast: Toast(title: "Message"),
            show: .constant(true),
            backgroundColor: Color.blue,
            foregroundColor: Color.white,
            paddingBottom: 40
        )
    }
}
