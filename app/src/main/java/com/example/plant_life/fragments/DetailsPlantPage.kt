package com.example.plant_life.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.plant_life.NotificationsActivity
import com.example.plant_life.databinding.FragmentDetailsPlantPageBinding
import com.example.plant_life.model.PlantViewModel

class DetailsPlantPage : Fragment() {
    private val viewModel: PlantViewModel by activityViewModels()
    var _binding: FragmentDetailsPlantPageBinding? = null
    private val binding get() = _binding

    lateinit var plant: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments.let {
            plant = it?.getString("id")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsPlantPageBinding.inflate(inflater)
//to  bind all the views in xml file
        _binding!!.lifecycleOwner = this
        _binding!!.viewModel1 = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "${plant}")
        viewModel.plantInfoData(plant)
//it's action for button from Details plant fragment to Notification activities
        binding?.alarmButton?.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "set alarm of watering and lighting ",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(
                this@DetailsPlantPage.requireContext(),
                NotificationsActivity::class.java
            )
            startActivity(intent)
        }

    }

}