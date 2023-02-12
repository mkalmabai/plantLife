package com.example.plant_life.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.plant_life.adapter.PlantAdapter
import com.example.plant_life.databinding.FragmentMyPlantFragmentBinding
import com.example.plant_life.model.PlantViewModel

class MyPlant_fragment : Fragment() {
    private val viewModel: PlantViewModel by viewModels()
    lateinit var binding: FragmentMyPlantFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantFragmentBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MyPlant_fragment.viewModel
            recyclerView.adapter = PlantAdapter(requireContext(), "MyPlant")
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "My Plant"
        viewModel.showPlantsList()
    }

}
