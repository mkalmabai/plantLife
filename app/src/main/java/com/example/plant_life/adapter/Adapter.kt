package com.example.plant_life.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant_life.R
import com.example.plant_life.dataApi.ResponseItem
import com.example.plant_life.databinding.ItemStyleBinding
import com.example.plant_life.fragments.HomeFragmentDirections
import com.example.plant_life.fragments.MyPlant_fragmentDirections
import com.example.plant_life.model.PlantViewModel.Companion.favPlantList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class PlantAdapter(private val context: Context, private var page: String) :
    ListAdapter<ResponseItem, PlantAdapter.PlantInfoViewHolder>(DiffCallback) {
    private val myPlantCollection = Firebase.firestore.collection("User profiles")

    inner class PlantInfoViewHolder(
        var binding:
        ItemStyleBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val card: CardView = binding.cardView
        val buttonShare = binding.buttonShare

// bind the plantInfo ii is variable in xml of item style
        fun bind(resultsItems: ResponseItem) {
            binding.plantInfo = resultsItems
    Log.e("TAG", "bind: ${resultsItems.PlantName}", )
            binding.executePendingBindings()
        }
    }

//search about id of plant if in the fav list or not
    private fun isFav(item: ResponseItem) =
        favPlantList.MyPlantList.find {
            it.id == item.id
        }
// function for remove plant from "My Plant" page
    private fun removePlanetFromFav(resultsItems: ResponseItem) {
        favPlantList.MyPlantList.removeAll { it.id == resultsItems.id }
        myPlantCollection.document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .update("my_planet", favPlantList.MyPlantList)
    }

    private fun addPlanetToFav(
        item: ResponseItem
    ) {
        favPlantList.MyPlantList.add(item)// add the plant in MyPlant List
        myPlantCollection.document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .update("my_planet", favPlantList.MyPlantList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantInfoViewHolder {

        return PlantInfoViewHolder(ItemStyleBinding.inflate(LayoutInflater.from(parent.context)))

    }


    override fun onBindViewHolder(holder: PlantInfoViewHolder, position: Int) {
        val resultsItems = getItem(position)
        holder.bind(resultsItems)
        val item = isFav(resultsItems)

        if (item != null) {
            holder.binding.likeImg.setImageResource(R.drawable.favorite_filled)
        } else {
            holder.binding.likeImg.setImageResource(R.drawable.favorite_border)
        }


        //hold the card of more info for plant list "after that i created argument for home fragment in nav_graf"
        holder.card.setOnClickListener {
            if (page == "Home") {
                val action = HomeFragmentDirections.actionHomeFragmentToDitealsPlantPage(resultsItems.id)
                holder.itemView.findNavController().navigate(action)
            } else if (page == "MyPlant") {
                val action =
                    MyPlant_fragmentDirections.actionMayPlantFragmentToDitealsPlantPage(resultsItems.id)
                holder.itemView.findNavController().navigate(action)
            }

        }

        //this action to share link of plant image
        holder.buttonShare.setOnClickListener {
            shared(resultsItems)
        }

        //button to add plant to "my plant" and check if will put favorite_filled or favorite_border
        holder.binding.likeImg
            .setOnClickListener {
                val s = isFav(resultsItems)
                if (s == null) {
                    holder.binding.likeImg.setImageResource(R.drawable.favorite_filled)
                    addPlanetToFav(resultsItems)

                } else {
                    holder.binding.likeImg.setImageResource(R.drawable.favorite_border)
                    removePlanetFromFav(resultsItems)
                }
            }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<ResponseItem>() {
        override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //function to share link of image plant
    fun shared(resultsItems: ResponseItem) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
            // (Optional) Here we're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Introducing content previews")

                    //text write it with the link of image
                .putExtra(Intent.EXTRA_TEXT, "see my new plant ${resultsItems.image} ")
                    //the type of share content
                .setType("text/plain")
        }, null)
        context.startActivity(share)

    }


}