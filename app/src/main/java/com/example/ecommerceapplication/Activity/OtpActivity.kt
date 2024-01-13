package com.example.ecommerceapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.ActivityOtpBinding
import com.example.ecommerceapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OtpActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OtpActivity.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtnOtp.setOnClickListener {
            if(binding.etOtp.text!!.isEmpty()){
                Toast.makeText(this,"Please Provide OTP",Toast.LENGTH_SHORT).show()
            }else{
                verifyUser(binding.etOtp.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {

        val credential =  PhoneAuthProvider.getCredential(
            intent.getStringExtra("verificationId")!!,otp
        )
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
       FirebaseAuth.getInstance().signInWithCredential(credential)
           .addOnCompleteListener(this){task->

               val prefrence = this.getSharedPreferences("user", MODE_PRIVATE)
               val editer = prefrence.edit()

               editer.putString("number",intent.getStringExtra("number")!!)

               editer.apply()


               if(task.isSuccessful){
                   startActivity(Intent(this,MainActivity::class.java))
                   finish()
               }else{
                   Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
               }
           }
    }
}