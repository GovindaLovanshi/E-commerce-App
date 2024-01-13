package com.example.ecommerceapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.RoomDB.AppDatabase
import com.example.ecommerceapplication.RoomDB.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckOutActivity : AppCompatActivity(),PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        val checkout = Checkout()
        checkout.setKeyID("rzp_text_deTONJhwSZzH53")

        val price = intent.getStringExtra("totalCost")

        try {
            val option = JSONObject()
            option.put("name","Raju")
            option.put("description","E Commerce PlateForm")
            option.put("image","")//logo
            option.put("theme.color","#673AB7")
            option.put("currency","INR")
            if (price != null) {
                option.put("amount",(price.toInt()*100).toString())
            }
            option.put("prefill.contact","9864859644")
            checkout.open(this,option)
        }catch (e:Exception){
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(){
        Toast.makeText(this,"Payment Success",Toast.LENGTH_SHORT).show()
        
        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for(currentId in id!!){
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String?) {

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

                val dao = AppDatabase.getInstance(this),productDao()

                Firebase.firestore.collection("products")
                    .document(productId!!).get().addOnSuccessListener {
                        lifecycleScope.launch(Dispatchers.IO){
                            dao.deleteProduct(ProductModel(productId))
                        }
                    }
                saveData(it.getString("productName"),it.getString("productSp"),productId)
            }.addOnFailureListener {

            }
    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preference = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String,Any>()
        data["name"] =name!!
        data["price"] =price!!
        data["productId"] =productId!!
        data["status"] ="Ordered"
        data["userId"] =preference.getString("number","")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this,"Order Placed",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentError(){
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}