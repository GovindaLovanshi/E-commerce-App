package com.example.ecommerceapplication.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDatabase :RoomDatabase(){

    abstract fun productDao{
        companion object {
            private var database :AppDatabase? = null
            private var DATABASE_NAME = "ECommerce"

            @Synchronized

            fun getInstance(context: Context):AppDatabase{
               if(database == null){
                   database = Room.databaseBuilder(
                   context.applicationContext,
                   AppDatabase::class.java,
                   DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
               }
                return database!!
            }

        }
    }
    abstract fun productDao():Productdao
}