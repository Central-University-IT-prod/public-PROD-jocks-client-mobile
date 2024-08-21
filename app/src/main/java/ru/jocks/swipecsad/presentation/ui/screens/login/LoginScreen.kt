package ru.jocks.swipecsad.presentation.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations
import ru.jocks.uikit.FormField
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber


class LoginFormValidState (emailValidNow: Boolean, passwordValidNow: Boolean) {
    var emailValid = emailValidNow
    var passwordValid = passwordValidNow
}


@Composable
fun LoginScreen(navController: NavController) {
    val vm: LoginViewModel = getViewModel()
    val loginState = vm.loginState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navController.navigate(Destinations.Main.route) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarTitle(
                title = stringResource(id = R.string.login_screen_title),
                onBack = {
                    navController.popBackStack()
                }
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            var formState by remember {
                mutableStateOf(LoginFormValidState(emailValidNow = false, passwordValidNow = false))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                content = {
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_email),
                            value = email,
                            onValueChange = { it, valid ->
                                email = it
                                formState = LoginFormValidState(valid, formState.passwordValid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$",
                            errorText = stringResource(id = R.string.message_email_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_password),
                            value = password,
                            onValueChange = { it, valid ->
                                password = it
                                formState = LoginFormValidState(formState.emailValid, valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                            errorText = stringResource(id = R.string.message_password_requirements)
                        )
                    }
                }
            )

            Box(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Button(
                        onClick = {
                            Timber.tag("LoginScreen").d("$email, $password")
                            vm.login(
                                email,
                                password
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4EFF)),
                        enabled = formState.emailValid && formState.passwordValid
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.button_login),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
