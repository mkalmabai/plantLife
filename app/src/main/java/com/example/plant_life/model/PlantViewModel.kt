package com.example.plant_life.model

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant_life.NotificationsActivity
import com.example.plant_life.dataApi.*
import com.example.plant_life.fragments.HomeFragment
import com.example.plant_life.fragments.MyPlant_fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import kotlin.concurrent.timerTask
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.math.log

enum class PlantApiStatus { LOADING, ERROR, DONE }

class PlantViewModel : ViewModel() {
    private val _status = MutableLiveData<PlantApiStatus>()
    val status: LiveData<PlantApiStatus> = _status

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    private val _plantInfo = MutableLiveData<List<ResponseItem?>>()
    val plantInfo: LiveData<List<ResponseItem?>> = _plantInfo

    var _profilInfo = MutableLiveData<List<User?>>()
    val profilInfo: LiveData<List<User?>> = _profilInfo

    private val _response = MutableLiveData<Response?>()
    val response: LiveData<Response?> = _response

    private var _plantImage = MutableLiveData<String>()
    val plantImage: MutableLiveData<String> get() = _plantImage

    private var _plantImageBackground = MutableLiveData<String>()
    val plantImageBackground: MutableLiveData<String> get() = _plantImageBackground

    val _plantName = MutableLiveData<String>()
    val plantName: MutableLiveData<String> get() = _plantName

    val _Watering = MutableLiveData<String>()
    val Watering: MutableLiveData<String> get() = _Watering

    val _Lighting = MutableLiveData<String>()
    val Lighting: MutableLiveData<String> get() = _Lighting

    val _Temperature = MutableLiveData<String>()
    val Temperature: MutableLiveData<String> get() = _Temperature

    var _favPlant = MutableLiveData<List<ResponseItem>>()
    val favPlant: MutableLiveData<List<ResponseItem>> get() = _favPlant

    val _firstName = MutableLiveData<String>()
    val firstName: MutableLiveData<String> get() = _firstName

    val _lastName = MutableLiveData<String>()
    val lastName: MutableLiveData<String> get() = _lastName

    val _Bio = MutableLiveData<String>()
    val Bio: MutableLiveData<String> get() = _Bio
    private val profileCollection = Firebase.firestore.collection("User profiles")
    var userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    init {
        getplantInfo()
    }

    fun getplantInfo() {
        viewModelScope.launch {
            _status.value = PlantApiStatus.LOADING
            try {
                _plantInfo.value = PlantApi.retrofitServer.getInfo().response
                // _state.value = PlantApi.retrofitServer.getInfo().response.toString()
                //  Log.e("TAG", "getplantInfo: ${  _plantInfo.value }", )
                //  _status.value = PlantApiStatus.DONE
            } catch (e: Exception) {
                Log.e("TAG", "getplantInfo:  error${e}")
                _status.value = PlantApiStatus.ERROR
                _plantInfo.value = listOf()
            }
        }
    }


    companion object {
        val favPlantList = MyPlantViewModel()
    }

    fun showUserInfo() {
        var doc = profileCollection.document(userId)
        Log.e("TAG", "showUserInfo: doc $doc")
        doc.get()
            .addOnCompleteListener { task ->
                _firstName.value = task.result.data?.get("firstName").toString()
                _lastName.value = task.result.data?.get("lastName").toString()
                _Bio.value = task.result.data?.get("bio").toString()
            }.addOnFailureListener { println(it.message) }
    }

    fun plantInfoData(position: String) {
        for (item in _plantInfo.value!!) {
            if (item?.id == position) {
                _plantImage.value = item?.image
                _plantName.value = item?.PlantName
                _plantImageBackground.value = item?.backgroundImage
                _Watering.value = item?.Watering
                _Lighting.value = item?.Lighting
                _Temperature.value = item?.Temperature
            }
        }
    }

    fun showPlantsList() {
        profileCollection.document(userId)
            .get()
            .addOnSuccessListener { task ->
                val dataList = task.get("my_planet")
                val s = Gson().toJson(dataList)
                val listType: Type = object : TypeToken<List<ResponseItem>?>() {}.type
                val userFaveList: List<ResponseItem> = Gson().fromJson(s, listType) ?: emptyList()
                favPlantList.MyPlantList.clear()
                favPlantList.MyPlantList.addAll(userFaveList)
                _favPlant.value = userFaveList
            }.addOnFailureListener {
                println(it.message)
            }
    }
}

