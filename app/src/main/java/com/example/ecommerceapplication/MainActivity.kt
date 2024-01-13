package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerceapplication.Activity.LoginActivity
import com.example.ecommerceapplication.databinding.ActivityMainBinding
import com.google.android.play.core.integrity.i
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popMenu = PopupMenu(this, null)
        popMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popMenu.menu, navController)

        binding.bottomBar.setOnItemSelectedListener {
            when (it) {
                0 -> {
                    0.also { i = it }
                    navController.navigate(R.id.homeFragment)

                }

                1 -> i = 1
                2 -> i = 2
            }


        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i == 0){
            finish()
        }

    }
}



