package com.example.ecommerceapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapplication.databinding.AllOrderItemLayoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class AllOrderAdapter {

    class AllOrderAdapter(val list: ArrayList<AllOrderModel>, val context: Context) :
        RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {
        inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
            return AllOrderViewHolder(
                AllOrderItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
            holder.binding.productItem.text = list[position].name
            holder.binding.productItemPrice.text = list[position].price
//

            when (list[position].status) {
                "Ordered" -> {
                    holder.binding.productDelivered.text = "Ordered"
                }


                "Dispatched" -> {
                    holder.binding.productDelivered.text = "Dispatched"

                }

                "Delivered" -> {
                    holder.binding.productDelivered.text = "Delivered"

                }

                "Canceled" -> {
                    holder.binding.productDelivered.text = "Canceled"

                }


            }
        }


        override fun getItemCount(): Int {
            return list.size
        }
    }
}