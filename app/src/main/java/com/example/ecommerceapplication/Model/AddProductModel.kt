package com.example.ecommerceapplication.Model

data class AddProductModel(
    val productName: String? = "",
    val productDescription: String? = "",
    val productCoverImage: String? = "",
    val productCategory: String? = "",
    val productId: String? = "",
    val productMRP: String? = "",
    val productSp: String? = "",
    val productImages: ArrayList<String> = ArrayList()
)
