package com.dinaro.firebaseauth;

import com.google.firebase.auth.PhoneAuthProvider;

public interface FirebasePhoneAuthListener {

    void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token);
    void onVerificationFailed();
    void onVerificationSuccess();
}
