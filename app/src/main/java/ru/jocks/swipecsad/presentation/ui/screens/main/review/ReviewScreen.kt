package ru.jocks.swipecsad.presentation.ui.screens.main.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.koin.java.KoinJavaComponent
import ru.jocks.domain.review.models.RatingFormItem
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.screens.main.MainViewModel
import ru.jocks.swipecsad.presentation.ui.screens.main.place.RatingStars
import ru.jocks.uikit.CustomButton
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber


@Composable
fun ReviewScreen(navController: NavController, link : Int) {
    val vm: ReviewViewModel by KoinJavaComponent.inject(ReviewViewModel::class.java)

    LaunchedEffect(Unit) {
        vm.getFields(link)
    }
    var canSend by remember { mutableStateOf(false) }
   Column() {
       TopBarTitle(title = stringResource(id = R.string.review)) {
           navController.popBackStack()
       }


       Box {
           LazyColumn(Modifier.padding(horizontal = 16.dp)) {
               if (vm.reviewFieldsUiState is ReviewFieldsUiState.Success) {
                   items((vm.reviewFieldsUiState as? ReviewFieldsUiState.Success)?.fields?.baseFields ?: emptyList()) { review ->
                       var rating by remember {
                           mutableStateOf(review.rating)
                       }
                       ReviewItem(review.name, rating.toDouble()) {
                           review.rating = it.toInt()
                           rating = it.toInt()
                           canSend = checkCanSendReview((vm.reviewFieldsUiState as? ReviewFieldsUiState.Success)!!)
                       }
                   }
               }
           }
           Column(horizontalAlignment = Alignment.CenterHorizontally) {
               Spacer(Modifier.weight(1f))
               CustomButton(
                   text = stringResource(id = R.string.next),
                   buttonModifier = Modifier
                       .fillMaxWidth()
                       .wrapContentHeight()
                       .padding(horizontal = 12.dp),
                   textModifier = Modifier.padding(vertical = 8.dp),
                   enabled = canSend
               ) {
                   navController.navigate(AppScreens.ReviewGoods.name)
               }
               if (canSend) {
                   Spacer(Modifier.height(4.dp))
                   Text(
                       text = stringResource(id = R.string.not_set_goods_rating),
                       style = MaterialTheme.typography.bodyMedium,
                       modifier = Modifier.clickable {
                           navController.navigate(AppScreens.ReviewReport.name)
                       }
                   )
               }
               Spacer(Modifier.height(14.dp))
           }
       }
   }
}

@Composable
fun ReviewItem(
    name : String,
    rating : Double,
    onRatingChange : (Double) -> Unit
) {
    Column {
        Text(
            name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top=12.dp)
        )
        RatingStars(rating = rating, size = 32.dp) {
            onRatingChange(it)
        }
    }
}

fun checkCanSendReview(review : ReviewFieldsUiState.Success) : Boolean {
    return review.fields.baseFields.all {
        it.rating != 0
    }
}