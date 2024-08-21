package ru.jocks.swipecsad.presentation.ui.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.custom.ProfileForm
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations


@Composable
fun RegistrationScreen(navController: NavController) {
    val vm: RegistrationViewModel = getViewModel()

    val registrationState = vm.registerState

    Column {
        if (registrationState is RegistrationState.Error) {
            Text(
                text = registrationState.message,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))
        }

        LaunchedEffect(registrationState) {

            if (registrationState is RegistrationState.Success) {
                navController.navigate(Destinations.Main.route) {
                    popUpTo(0)
                }
            }
        }

        ProfileForm(
            navController,
            actionName = stringResource(id = R.string.button_register),
            showPolicy = true,
            onSend = { email,
                       username,
                       password, ->
                vm.register(email, username, password)
            },
            title = stringResource(id = R.string.register_screen_title)
        )
    }
}
