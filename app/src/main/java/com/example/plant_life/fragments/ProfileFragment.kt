package com.example.plant_life.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.plant_life.*
import com.example.plant_life.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.plant_life.dataApi.User
import com.google.firebase.auth.ktx.auth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val profileCollection = Firebase.firestore.collection("User profiles")

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// handle click - logout
        firebaseAuth = FirebaseAuth.getInstance()
        binding!!.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent =
                Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
// handle click - save changes of user information
        binding!!.SaveButton.setOnClickListener {
            addUser(createUser())
            val action = ProfileFragmentDirections.actionProfileFragmentToUserProileInfoFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    //creat a new user on firebase
    fun createUser(): User {
        var idUser = Firebase.auth.currentUser!!.uid
        var firstName = binding!!.etFirstName.text.toString()
        var lastName = binding!!.etLastName.text.toString()
        var bio = binding!!.etBio.text.toString()
        return User(idUser, firstName, lastName, bio, mapOf())
    }

    // Add a new document with a generated ID
    fun addUser(profile: User) {
        profileCollection.document(FirebaseAuth.getInstance().currentUser?.uid ?: "").set(profile)
            .addOnCompleteListener {
                Log.d("TAG", "addUser: ${it.isSuccessful}")
            }
    }
}







