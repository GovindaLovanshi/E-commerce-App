package com.example.ecommerceapplication.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerceapplication.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var  prefrence : SharedPreferences
    private lateinit var totalCost:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        prefrence =  this.getSharedPreferences("user", MODE_PRIVATE)
        setContentView(binding.root)

        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.addressBtn.setOnClickListener {
            validateData(
                binding.nameET.text.toString(),
                binding.mobileET.text.toString(),
                binding.villageET.text.toString(),
                binding.cityET.text.toString(),
                binding.stateET.text.toString(),
                binding.pinCodeET.text.toString(),
            )

        }
    }

    private fun validateData(
        name: String,
        number: String,
        village: String,
        city: String,
        state: String,
        pinCode: String
    ) {
      if(number.isEmpty() || name.isEmpty() || village.isEmpty() || city.isEmpty()|| state.isEmpty()||pinCode.isEmpty()){
          Toast.makeText(this,"Please Fill ALl Feilds",Toast.LENGTH_SHORT).show()
      }else{
          storeData(village,city,state,pinCode)
      }
    }

    private fun storeData(village:String, city: String, state: String, pinCode: String) {
        val map = hashMapOf<String,Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(prefrence.getString("number","")!!)
            .update(map).addOnSuccessListener {

                val b = Bundle()
                b.putString("totalCost", totalCost)
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productsIds"))
                val intent = Intent(this,CheckOutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {


        Firebase.firestore.collection("users").document(prefrence.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.nameET.setText(it.getString("userName"))
                binding.mobileET.setText(it.getString("userPhoneNumber"))
                binding.villageET.setText(it.getString("village"))
                binding.cityET.setText(it.getString("city"))
                binding.stateET.setText(it.getString("state"))
                binding.pinCodeET.setText(it.getString("pinCode"))
            }.addOnFailureListener {

            }
    }


}