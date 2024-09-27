package com.example.gymsaround.gym.presentaion.gymslist

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gym.data.di.MainDispatcher
import com.example.gymsaround.gym.domain.list.GetInitialGymsUseCase
import com.example.gymsaround.gym.domain.list.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    // private val stateHandle: SavedStateHandle
    private val getInitialGymsUseCase : GetInitialGymsUseCase,
    private val toggleFavouriteStateUseCase : ToggleFavouriteStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private var _state by mutableStateOf(
        GymsScreenState(
            emptyList(),
            true,
        )
    )

    val state: State<GymsScreenState> get() = derivedStateOf { _state }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        run {
            throwable.printStackTrace()
            _state = _state.copy(
                isLoading = false,
                error = throwable.message
            )
        }
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler + dispatcher) {
            val receivedGyms = getInitialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }
    }

    fun toggleFavouriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler+dispatcher) {
            val updatedGymsList =
                toggleFavouriteStateUseCase(gymId, oldValue)
            _state = _state.copy(
                gyms = updatedGymsList,
            )
        }
    }

//    private fun getGyms() {
//        viewModelScope.launch(errorHandler) {
//            val gyms = getAllGyms()
//            state = gyms.restoreSelectedGyms()
//        }
//    }


//    fun toggleFavouriteState(gymId: Int) {
//        val gyms = state.toMutableList()
//        val itemIndex = gyms.indexOfFirst { it.id == gymId }
//        gyms[itemIndex] = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
//        storeSelectedGym(gyms[itemIndex])
//        //state = gyms
//        viewModelScope.launch {
//            val updatedGymsList = toggleFavouriteGym(gymId, gyms[itemIndex].isFavourite)
//            state = updatedGymsList
//        }
//    }


//    private fun storeSelectedGym(gym: Gym) {
//        val saveHandleList = stateHandle.get<List<Int>>(FAV_IDS).orEmpty().toMutableList()
//        if (gym.isFavourite) saveHandleList.add(gym.id)
//        else saveHandleList.remove(gym.id)
//        stateHandle[FAV_IDS] = saveHandleList
//    }
//
//    private fun List<Gym>.restoreSelectedGyms(): List<Gym> {
//        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
//            val gymsMap = this.associateBy { it.id }.toMutableMap()
//            savedIds.forEach { gymId ->
//                val gym = gymsMap[gymId] ?: return@forEach
//                gymsMap[gymId] = gym.copy(isFavourite = true)
//            }
//            return gymsMap.values.toList()
//        }
//        return this
//    }

    companion object {
        const val FAV_IDS = "favouriteGymIds"
    }
}