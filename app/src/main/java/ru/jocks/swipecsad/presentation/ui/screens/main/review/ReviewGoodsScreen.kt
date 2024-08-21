package ru.jocks.swipecsad.presentation.ui.screens.main.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.java.KoinJavaComponent
import ru.jocks.domain.review.models.RatingFormGoodItem
import ru.jocks.domain.review.models.RatingFormItem
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.screens.main.place.RatingStars
import ru.jocks.uikit.CustomButton
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber


@Composable
fun ReviewGoodsScreen(navController: NavController) {
   val vm: ReviewViewModel by KoinJavaComponent.inject(ReviewViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    Timber.i("goods screen ${vm.reviewFieldsUiState}")
   Column() {
       TopBarTitle(title = stringResource(id = R.string.review)) {
           navController.popBackStack()
       }

       Box {
               LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                   if (vm.reviewFieldsUiState is ReviewFieldsUiState.Success) {
                   items((vm.reviewFieldsUiState as ReviewFieldsUiState.Success).fields.goodsFields) { good ->
                       var rating by remember {
                           mutableStateOf(good.rating)
                       }
                       ReviewGoodItem(good.name, rating.toDouble()) {
                           good.rating = it.toInt()
                           rating = it.toInt()
                       }
                   }
               }
           }

           Column(horizontalAlignment = Alignment.CenterHorizontally) {
               Spacer(Modifier.weight(1f))
               CustomButton(
                   text = stringResource(id = R.string.send_report),
                   buttonModifier = Modifier
                       .fillMaxWidth()
                       .wrapContentHeight()
                       .padding(horizontal = 12.dp),
                   textModifier = Modifier.padding(vertical = 8.dp)
               ) {
                   coroutineScope.launch {
                       val result = vm.sendReport()
                       if (result) {
                           navController.navigate(AppScreens.ReviewReport.name)
                       }
                   }
               }
               Spacer(Modifier.height(14.dp))
           }
       }

   }
}

@Composable
fun ReviewGoodItem(
    name : String,
    rating : Double?,
    onRatingChange : (Double) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top=12.dp)
        )
        Spacer(Modifier.weight(1f))
        if (rating != null) {
            RatingStars(rating = rating, size = 24.dp) {
                onRatingChange(it)
            }
        } else {
            IconButton(onClick = { onRatingChange(5.0) }) {
                Image(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

    }
}