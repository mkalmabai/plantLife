package com.example.plant_life.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.plant_life.NotificationsActivity
import com.example.plant_life.adapter.PlantAdapter
import com.example.plant_life.databinding.FragmentHomeFragmentBinding
import com.example.plant_life.databinding.ItemStyleBinding
import com.example.plant_life.model.PlantViewModel


class HomeFragment : Fragment() {

    private val plantViewModel: PlantViewModel by activityViewModels()
    var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding
    private var binding2: ItemStyleBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeFragmentBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        plantViewModel.getplantInfo()
        binding?.apply {
            Log.e("TAG", "onCreateView:  binding?.apply ${plantViewModel?.state.value}")
            lifecycleOwner = viewLifecycleOwner
            viewModel = plantViewModel
            recyclerView.adapter = PlantAdapter(requireContext(), "Home")
        }
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}


