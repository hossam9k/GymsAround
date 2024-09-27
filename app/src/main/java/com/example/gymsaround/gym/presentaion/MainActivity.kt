package com.example.gymsaround.gym.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.gymsaround.gym.presentaion.details.GymDetailsScreen
import com.example.gymsaround.gym.presentaion.details.GymDetailsViewModel
import com.example.gymsaround.gym.presentaion.gymslist.GymsScreen
import com.example.gymsaround.gym.presentaion.gymslist.GymsViewModel
import com.example.gymsaround.ui.theme.GymsAroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            GymsAroundTheme {
                GymsAroundApp()
            }
        }
    }
}

@Composable
private fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            val vm: GymsViewModel = hiltViewModel()
            GymsScreen(
                vm.state.value,
                onItemClick = { id ->
                    navController.navigate(
                        "gyms/$id"
                    )
                },
                onFavouriteItemClick = { id, oldValue ->
                    vm.toggleFavouriteState(id, oldValue)
                }
            )
        }
        composable(route = "gyms/{gym_id}",
            arguments = listOf(
                navArgument("gym_id") {
                    type = NavType.IntType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.gymsaround.com/details/{gym_id}"
                }
            )
        ) {
            //val gymId = it.arguments?.getInt("gym_id")
            val detailsViewModel : GymDetailsViewModel = hiltViewModel()
            GymDetailsScreen(detailsViewModel.state)
        }
    }
}

