package com.example.gymsaround

import com.example.gymsaround.gym.data.GymsRepository
import com.example.gymsaround.gym.domain.list.GetSortedGymsUseCase
import com.example.gymsaround.gym.domain.list.ToggleFavouriteStateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleFavouriteStateUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toggleFavouriteState_updatesFavouriteRepository() = scope.runTest{
        //setup
        val gymsRepo = GymsRepository(TestGymsApiService(),TestGymsDao(),dispatcher)
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepo)
        val useCaseUnderTest = ToggleFavouriteStateUseCase(gymsRepo,getSortedGymsUseCase)

        gymsRepo.loadGyms()
        advanceUntilIdle()

        val gyms = DummyGymsList.getDomainDummyGymsList()
        val gymUnderTest = gyms[0]
        val isFav = gymUnderTest.isFavourite

        val updateGymsList = useCaseUnderTest(gymUnderTest.id,isFav)
        advanceUntilIdle()

        //assertion
        assert(updateGymsList[0].isFavourite == !isFav)

    }
}