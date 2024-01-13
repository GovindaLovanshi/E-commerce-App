package com.example.ecommerceapplication.RoomDB

import androidx.room.PrimaryKey
import org.checkerframework.checker.nullness.qual.NonNull

@Entity(tableName = "products")

data class ProductModel(
    @PrimaryKey
    @NonNullval
    val productId :String,
    @ColumInfo(name = "productName")
    val productName:String? = "",
    @ColumnInfo(name = "productImage")
    val productImage :String? = "",
    @ColumnInfo(name = "productSp")
    val productSp : String? = "",

)
