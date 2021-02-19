package com.cedric.foodforfamily

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_auth.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {

    var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                code = p0.smsCode.toString()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Log.i("Test send", p0)
                code = p0
            }

        }

        btnPhone.onClick {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+32471563620",
                100,
                TimeUnit.SECONDS,
                this@PhoneAuthActivity,
                callback
            )
        }
    }

}
