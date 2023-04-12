//
//  AuthManager.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.07.2022.
//

import Foundation
import FirebaseAuth

class AuthManager {
    private let auth = Auth.auth()

    private var verificationId:String?
    
    func getCurrentUserUuid() -> String?{
        return auth.currentUser?.uid
    }
    func getCurrentUserPhone() -> String?{
        return auth.currentUser?.phoneNumber
    }
    
    public func startAuth(phoneNumber:String, completion: @escaping(Bool) -> Void){
        PhoneAuthProvider.provider().verifyPhoneNumber(phoneNumber, uiDelegate: nil){ [weak self] verificationId, error in
            guard let verificationId = verificationId, error == nil else{
                completion(false)
                return
            }
            self?.verificationId = verificationId
            completion(true)
        }
    }
    
    public func verifyCode(smsCode:String, completion:@escaping (Bool) -> Void){
        guard let verificationId = verificationId else {
            completion(false)
            return
        }

        let crendential = PhoneAuthProvider.provider()
            .credential(withVerificationID: verificationId, verificationCode: smsCode)

        auth.signIn(with: crendential){ result, error in
            guard result != nil, error == nil else{
                completion(false)
                return
            }
            completion(true)
        }
    }
    
    func logout(){
        do { try Auth.auth().signOut() }
        catch { print("already logged out") }
    }
    
}
