package com.example.gymsaround.gym.presentaion.gymslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.gymsaround.gym.domain.Gym
import com.example.gymsaround.gym.presentaion.SemanticsDescription
import com.example.gymsaround.ui.theme.Purple80

@Composable
fun GymsScreen(
    state: GymsScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteItemClick: (Int, oldValue: Boolean) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn {
            items(state.gyms) { gym ->
                GymItem(
                    gym = gym,
                    onFavouriteItemClick = { id, oldValue ->
                        onFavouriteItemClick(
                            id, oldValue
                        )
                    },
                    onItemClick = { id ->
                        onItemClick(id)
                    }
                )
            }
        }
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier.semantics {
                this.contentDescription = SemanticsDescription.GYM_LIST_LOADING
            }
        )
        state.error?.let { Text(it) }
    }
}

@Composable
fun GymItem(gym: Gym, onFavouriteItemClick: (Int, Boolean) -> Unit, onItemClick: (Int) -> Unit) {
    val icon = if (gym.isFavourite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClick(gym.id)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                8.dp
            )
        ) {
            DefaultIcon(Icons.Filled.Place, Modifier.weight(15f), contentDescription = "")
            GymDetails(gym, Modifier.weight(70f))
            DefaultIcon(icon, Modifier.weight(15f), contentDescription = "") {
                onFavouriteItemClick(gym.id, gym.isFavourite)
            }

        }
    }
}

@Composable
fun GymDetails(
    gym: Gym,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.titleLarge,
            color = Purple80
        )
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            Text(
                text = gym.place,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewGymScreen() {
//    GymsAroundTheme {
//        GymsScreen()
//    }
//}
