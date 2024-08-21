package ru.jocks.swipecsad.presentation.ui.screens.placeDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations
import ru.jocks.swipecsad.presentation.ui.screens.main.place.PlaceInfo
import ru.jocks.uikit.CustomButton
import ru.jocks.uikit.TopBarTitle

@Composable
fun PlaceDetailsScreen(navController: NavController, id : Int) {
  val vm : PlaceDetailsViewModel = viewModel()

  LaunchedEffect(Unit) {
      vm.getPlace(id)
  }


  Box {
      LazyColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
          item {
              TopBarTitle(stringResource(id = R.string.place_details)) {
                  navController.popBackStack()
              }
          }


          if (vm.placeUiState is PlaceDetailsUiState.Success) {
              val place = (vm.placeUiState as PlaceDetailsUiState.Success).place
              item {
                  PlaceInfo(
                      name = place.name,
                      rating = place.rating,
                      ratingCount = place.ratingCount,
                      description = place.description,
                      imageUrl = place.image
                  )
              }
              items(place.items) {
                  ItemReview(
                      it.name,
                      it.rating
                  )
              }
          } else {
              item {
                  CircularProgressIndicator()
              }
          }

          item {
              Spacer(modifier = Modifier.height(75.dp))
          }
      }
      Column {
          Spacer(modifier = Modifier.weight(1f))
          CustomButton(
              text = stringResource(id = R.string.write_review),
              buttonModifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight()
                  .padding(24.dp),
              textModifier = Modifier.padding(vertical = 8.dp)
          ) {
              navController.navigate(AppScreens.QrScanner.name)
//              navController.navigate("${AppScreens.Review.name}/8/")

          }
      }
  }
}