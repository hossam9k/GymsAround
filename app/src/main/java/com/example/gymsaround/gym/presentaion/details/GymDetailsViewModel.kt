package com.example.gymsaround.gym.presentaion.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gym.data.remote.GymsApiService
import com.example.gymsaround.gym.domain.details.GetGymDetailsUseCase
import com.example.gymsaround.gym.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class GymDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gymDetailsUseCase: GetGymDetailsUseCase
) : ViewModel() {


    private var apiService: GymsApiService
    val state = mutableStateOf<Gym?>(null)

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cairo-gyms-e62dd-default-rtdb.firebaseio.com/")
            .build()

        apiService = retrofit.create(GymsApiService::class.java)
        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

//    private fun getGym(id: Int) {
//        viewModelScope.launch {
//            val gym = getGymFromRemoteDB(id)
//            state.value = gym
//        }
//    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            state.value = gymDetailsUseCase(id)
        }
    }

//    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
//        apiService.getGym(id).values.first()
//    }
}