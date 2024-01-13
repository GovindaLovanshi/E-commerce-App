package com.example.ecommerceapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerceapplication.Model.UserModel
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.ActivitySignUpBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        private lateinit var binding: ActivitySignUpBinding
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.logInBtnS.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.SIgnUPBtnS.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        fun storeData() {
            val builder = AlertDialog.Builder(this)
                .setTitle("Loading....")
                .setMessage("Please Wait")
                .setCancelable(false)
                .create()
            builder.show()

//            val data = hashMapOf<String, Any>()
//            data["name"] = binding.etName.text.toString()
//            data["number"] = binding.etMBSignup.text.toString()

            val prefrence = this.getSharedPreferences("user", MODE_PRIVATE)
            val editer = prefrence.edit()

            editer.putString("number",binding.etMBSignup.text.toString())
            editer.putString("name",binding.etName.text.toString())
            editer.apply()


            val data = UserModel(userName = binding.etName.text.toString(),userPhoneNumber = binding.etName.text.toString())

            Firebase.firestore.collection("users").document(binding.etMBSignup.text.toString())
                .set(data).addOnSuccessListener {
                    Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                    builder.dismiss()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    builder.dismiss()

                }
        }

        private fun validateUser() {
            if (binding.etName.text!!.isEmpty() || binding.etMBSignup.text!!.isEmpty()) {
                Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show()
            } else {
                storeData()
            }

        }


    }
}