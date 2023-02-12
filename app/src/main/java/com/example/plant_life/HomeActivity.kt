package com.example.plant_life

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.plant_life.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityHomeBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Home"
        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        chekUser()
////        handle click - logout--------------------------------------
//        binding.logoutBtn.setOnClickListener{
//            firebaseAuth.signOut()
//            chekUser()
//        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.buttoa_nvigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
    }


    private fun chekUser() {
        //check User is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) { //User not null- User is logged in - get User info
            val email = firebaseUser.email
//---------related to display the email of current User
//            //set to text view
//            binding.emailTv.text = email
        } else {
            //User is null- User is not logged in - go to login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
