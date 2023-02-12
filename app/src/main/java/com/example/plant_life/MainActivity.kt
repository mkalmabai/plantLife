package com.example.plant_life

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.plant_life.databinding.ActivityLoginBinding
import com.example.plant_life.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding
//Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding1 = ActivityNotificationBindin.inflate()
        binding.start.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }

        //handel click alredy have acount
        binding.haveAccount.setOnClickListener {
            //validate data
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }


}






