package com.example.gymsaround.gym.presentaion.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.presentaion.gymslist.DefaultIcon
import com.example.gymsaround.gym.presentaion.gymslist.GymDetails

@Composable
fun GymDetailsScreen(state: MutableState<Gym?>) {
    val item = state.value

    item?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DefaultIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp),
                contentDescription = "Location Icon",
            )
            GymDetails(
                gym = item,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            Text(
                text = if (item.isOpen) "Gym is open" else "Gym is closed",
                color = if (item.isOpen) Color.Green else Color.Red
            )
        }
    }

}