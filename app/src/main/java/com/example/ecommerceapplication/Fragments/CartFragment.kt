package com.example.ecommerceapplication.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapplication.Activity.AddressActivity
import com.example.ecommerceapplication.Activity.CategoryActivity
import com.example.ecommerceapplication.Adapter.CartAdapter
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.RoomDB.AppDatabase
import com.example.ecommerceapplication.RoomDB.ProductModel
import com.example.ecommerceapplication.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var list :ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCartBinding.inflate(layoutInflater)


        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editer = preference.edit()
        editer.putBoolean("isCart", false)
        editer.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecyclerView.adapter = CartAdapter(requireContext(),it)

            list.clear()
            for(data in it){
                list.add(data.productId)
            }
            totalCost(it)
        }

        binding.root


    }

    private fun totalCost(data: List<ProductModel>) {

        var total = 0
        for(item in data){
            total += item.productSp!!.toInt()
        }

        binding.textView13.text = "Total item in cart is ${data.size}"
        binding.textView14.text = "Total cost : $total"

        binding.checkOut.setOnClickListener {
            val intent = Intent(context,AddressActivity::class.java)
            val b = Bundle()
            b.putString("totalCost", total.toString())
            b.putStringArrayList("productIds",list)
            startActivity(intent)

        }
    }

}