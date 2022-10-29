//
//  ToastView.swift
//  iosApp
//
//  Created by Марк Шавловский on 17.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct Toast{
    let title:String
}

struct ToastView: View {
    let toast:Toast
    @Binding var show :Bool
    let backgroundColor: Color
    let foregaroundColor: Color

    var body: some View {
        VStack{
            Spacer()
            Text(toast.title)
                .padding(Diems.MEDIUM_PADDING)
                .frame(maxWidth: .infinity)
                .background(backgroundColor)
                .foregroundColor(foregaroundColor)
                .cornerRadius(Diems.MEDIUM_RADIUS)
                .padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth: .infinity)
        .transition(AnyTransition.move(edge: .bottom).combined(with: .opacity))
        .onAppear(){
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                withAnimation {
                    self.show = false
                }
            }
        }
    }
}
struct Overlay<T:View> :ViewModifier {
    @Binding var show:Bool
    let overlayView:T
    
    func body(content: Content) -> some View {
        ZStack{
            content
            if(show){
                overlayView
            }
        }
    }
}

extension View {
    func overlay<T:View>(overlayView:T, show: Binding<Bool>) -> some View{
        self.modifier(Overlay(show: show, overlayView: overlayView))
    }
}

struct ToastView_Previews: PreviewProvider {
    static var previews: some View {
        ToastView(
            toast: Toast(title: "title"),
            show: .constant(true),
            backgroundColor: Color.blue,
            foregaroundColor: Color.brown
        )
    }
}
