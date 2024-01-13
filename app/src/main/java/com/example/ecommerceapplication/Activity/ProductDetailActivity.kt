package com.example.ecommerceapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView.ScaleType
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.RoomDB.AppDatabase
import com.example.ecommerceapplication.RoomDB.ProductModel
import com.example.ecommerceapplication.RoomDB.Productdao
import com.example.ecommerceapplication.databinding.ActivityProductDetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetail(intent.getStringExtra("id"))
        setContentView(binding.root)
    }

    private fun getProductDetail(proId: String?) {

        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                binding.textView7.text = it.getString("ProductName")
                binding.textView8.text = it.getString("ProductSp")
                binding.textView9.text = it.getString("ProductDescription")
                binding.textView7.text = name
                binding.textView8.text = productSp
                binding.textView9.text = productDesc

                val slideList = ArrayList<SlideModel>()
                for (data in list) {
                    slideList.add(SlideModel(data, ScaleType.CENTER_CROP))
                }

                cartAction(proId,name,productSp,it.getString("ProductCoverImg"))

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {
                Toast.makeText(this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun cartAction(proId: String,name:String?,productSP:String?,coverImg:String?){
        val productDao = AppDatabase.getInstance(this).productDao()

        if(productDao.isExit(proId) != null){
           binding.textView10.text = "GO TO Cart"
        }else{
            binding.textView10.text = "Add to Cart"
        }

        binding.textView10.setOnClickListener {
            if(productDao.isExit(proId) != null){
                openCart()
            }else{
                addCart(productDao,proId,name,productSP,coverImg)
            }
        }
    }

    private fun addCart(
        productDao:Productdao,
        proId: String,
        name: String?,
        productSP: String?,
        coverImg: String?
    ) {
        val data  = ProductModel(proId,name,coverImg,productSP)
        lifecycleScope.launch (Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView10.text = "Go To cart"
        }
    }



    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editer = preference.edit()
        editer.putBoolean("isCart",true)
        editer.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}