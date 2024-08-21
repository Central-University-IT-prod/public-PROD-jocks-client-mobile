package ru.jocks.swipecsad.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.custom.Coupon
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.uikit.CustomButton

@Composable
fun ProfileScreen(navController: NavController) {
    val vm: ProfileViewModel = getViewModel()
    val coupons = vm.promocodesUiState
   Box {
       Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
           Spacer(modifier = Modifier.height(12.dp))
           if (vm.userName!=null) {
               Text(
                   vm.userName,
                   style = MaterialTheme.typography.headlineLarge
               )
           }
           Spacer(modifier = Modifier.height(16.dp))
           LazyColumn(verticalArrangement = Arrangement.spacedBy(36.dp)) {
               item {}
               if (coupons is PromocodesUiState.Success) {
                   items(coupons.promocodes) {
                       Coupon(title = it.name, text = it.description, code = it.token)
                   }
               }
               item {
                   Spacer(Modifier.height(50.dp))
               }
           }
       }
       Column {
           Spacer(modifier = Modifier.weight(1f))
           CustomButton(
               text = stringResource(id = R.string.logout),
               buttonModifier = Modifier.fillMaxWidth().wrapContentHeight().padding(24.dp),
               textModifier = Modifier.padding(vertical = 8.dp)
           ) {
               vm.logout()
               navController.navigate(AppScreens.Intro.name) {
                   popUpTo(0)
               }
           }
       }
   }
}