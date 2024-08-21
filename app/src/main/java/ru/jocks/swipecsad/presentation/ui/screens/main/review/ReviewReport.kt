package ru.jocks.swipecsad.presentation.ui.screens.main.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.java.KoinJavaComponent
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.custom.Coupon
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations
import ru.jocks.uikit.CustomButton
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber

@Composable
fun ReviewReport(navController: NavController) {
    val vm: ReviewViewModel by KoinJavaComponent.inject(ReviewViewModel::class.java)

    TopBarTitle(title = stringResource(id = R.string.review)) {
        navController.popBackStack()
    }
    Box(contentAlignment = Alignment.Center) {
        Column(Modifier.wrapContentHeight().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.thanks_for_review),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.your_coupon),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(30.dp))
            Timber.i("vm.reviewResponseUiState ${vm.reviewResponseUiState}")
            if (vm.reviewResponseUiState is ReviewResponseUiState.Success) {
                val coupon = (vm.reviewResponseUiState as ReviewResponseUiState.Success).promoCode
                Coupon(title = coupon.name, text = coupon.description, code = coupon.token)
            }
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = stringResource(id = R.string.to_main_screen),
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp)
            ) {
                navController.navigate(Destinations.Main.route)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}