package com.example.ecommerceapplication.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapplication.Adapter.CategoryProductAdapter
import com.example.ecommerceapplication.Adapter.ProductAdapter
import com.example.ecommerceapplication.Model.AddProductModel
import com.example.ecommerceapplication.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    private fun getProducts(category: String) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
        list.clear()
            .get().addOnSuccessListener{
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewCategory)
                recyclerView.adapter = CategoryProductAdapter(this,list)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        intent.getStringExtra("cat")?.let { getProducts(it) }
    }
}