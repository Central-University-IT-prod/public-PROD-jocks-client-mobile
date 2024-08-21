package ru.jocks.swipecsad.presentation.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.custom.LoadingIndicator
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.screens.main.place.PlaceCard
import ru.jocks.swipecsad.presentation.ui.screens.map.PlacesUiState
import ru.jocks.uikit.CustomButton


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(navController: NavController) {
    var searchValue by remember { mutableStateOf("") }
    val vm: MainViewModel = getViewModel()

    val items = vm.recommendationsUiState

    val pullRefreshState = rememberPullRefreshState(
        refreshing = items is PlacesUiState.Loading,
        onRefresh = { vm.getPlaces() }
    )

    Column(Modifier.padding(horizontal = 24.dp)) {
        Text(
            stringResource(id = R.string.near_places),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top=12.dp)
        )
        Spacer(Modifier.height(24.dp))
        Search(searchValue = searchValue, onValueChange = {
            searchValue = it
            vm.setSearch(it)
//            items.refresh()
        })
        Spacer(Modifier.height(24.dp))
        CustomButton(
            text = stringResource(id = R.string.scan_qr),
            buttonModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal=12.dp),
            textModifier = Modifier.padding(vertical = 8.dp)
        ) {
            navController.navigate(AppScreens.QrScanner.name)
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp),modifier=Modifier.pullRefresh(pullRefreshState)) {
            item {}
            if (items is PlacesUiState.Loading) {
                item { LoadingIndicator() }
            }
            if (items is PlacesUiState.Success) {
                val placesItems = items.places
                items(placesItems) { item ->
                    PlaceCard(
                        name = item.name,
                        rating = item.rating,
                        ratingCount = item.ratingCount,
                        description = item.description,
                        imageUrl = item.image
                    ) {
                        navController.navigate("${AppScreens.PlaceDetails.name}/${item.id}")
                    }
                }
            }
            item { }
        }
    }
}

@Composable
fun Search(searchValue: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchValue,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, modifier=Modifier.size(16.dp))
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),

    )
}

