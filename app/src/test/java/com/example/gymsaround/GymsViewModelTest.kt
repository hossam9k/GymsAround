package com.example.gymsaround

import androidx.compose.runtime.ExperimentalComposeApi
import com.example.gymsaround.gym.data.GymsRepository
import com.example.gymsaround.gym.data.local.GymsDao
import com.example.gymsaround.gym.data.local.LocalGym
import com.example.gymsaround.gym.data.local.LocalGymFavouriteState
import com.example.gymsaround.gym.data.remote.GymsApiService
import com.example.gymsaround.gym.data.remote.RemoteGym
import com.example.gymsaround.gym.domain.list.GetInitialGymsUseCase
import com.example.gymsaround.gym.domain.list.GetSortedGymsUseCase
import com.example.gymsaround.gym.domain.list.ToggleFavouriteStateUseCase
import com.example.gymsaround.gym.presentaion.gymslist.GymsScreenState
import com.example.gymsaround.gym.presentaion.gymslist.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalComposeApi
class GymsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun loadingState_isLoadedCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        val state = viewModel.state.value

        assert(
            state == GymsScreenState(
                gyms = emptyList(),
                isLoading = true,
                error = null,
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadedContentState_isSetCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        advanceUntilIdle()
        val state = viewModel.state.value
        assert(
            state == GymsScreenState(
                gyms = DummyGymsList.getDomainDummyGymsList(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getViewModel(): GymsViewModel {
        val gymsRepository = GymsRepository(TestGymsApiService(), TestGymsDao(),dispatcher)
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepository)
        val getInitialGymsUseCase = GetInitialGymsUseCase(gymsRepository, getSortedGymsUseCase)
        val toggleFavouriteStateUseCase =
            ToggleFavouriteStateUseCase(gymsRepository, getSortedGymsUseCase)
        return GymsViewModel(getInitialGymsUseCase, toggleFavouriteStateUseCase, dispatcher)
    }

    class TestGymsApiService : GymsApiService {

        override suspend fun getGyms(): List<RemoteGym> {
            return DummyGymsList.getDummyGymsList()
        }

        override suspend fun getGym(id: Int): Map<String, RemoteGym> {
            TODO("Not yet implemented")
        }

    }

    class TestGymsDao : GymsDao {
        val gyms = HashMap<Int, LocalGym>()

        override suspend fun getAll(): List<LocalGym> {
            return gyms.values.toList()
        }

        override suspend fun addAll(gyms: List<LocalGym>) {
            return gyms.forEach {
                this.gyms[it.id] = it
            }
        }

        override suspend fun update(localGymFavouriteState: LocalGymFavouriteState) {
            updateGym(localGymFavouriteState)
        }

        override suspend fun getFavouriteGyms(): List<LocalGym> {
            return gyms.values.toList().filter { it.isFavourite }
        }

        override suspend fun updateAll(gyms: List<LocalGymFavouriteState>) {
            gyms.forEach {
                updateGym(it)
            }
        }

        //TODO("refactor")
        override suspend fun getGym(id: Int): LocalGym {
            return LocalGym(0, "n0", "p0", false)
        }

        private fun updateGym(gymState: LocalGymFavouriteState) {
            val gym = this.gyms[gymState.id]
            gym?.let {
                this.gyms[gymState.id] = gym.copy(isFavourite = gymState.isFavourite)
            }
        }

    }
}