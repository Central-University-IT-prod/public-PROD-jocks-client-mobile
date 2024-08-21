package ru.jocks.swipecsad.presentation.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.jocks.swipecsad.R
import ru.jocks.uikit.FormField


class RegisterFormValidState(
    private var emailValid: Boolean = false,
    private var usernameValid: Boolean = false,
    private var passwordValid: Boolean = false,
) {
    fun copy(
        emailValidNow: Boolean? = null,
        usernameValidNow: Boolean? = null,
        passwordValidNow: Boolean? = null,
    ): RegisterFormValidState {
        return RegisterFormValidState(
            emailValid = emailValidNow ?: emailValid,
            passwordValid = passwordValidNow ?: passwordValid,
            usernameValid = usernameValidNow ?: usernameValid
        )
    }

    fun isValid(): Boolean {
        return emailValid && passwordValid && usernameValid
    }
}

@Composable
fun ProfileForm(
    navController: NavController,
    actionName: String,
    title: String,
    showPolicy: Boolean,
    onSend: (
        email: String,
        username: String,
        password: String,
    ) -> Unit,
    message: String = "",
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarTitle(
                title = title
            ) {
                navController.popBackStack()
            }
        },
    ) { padding ->
        var formState by remember {
            mutableStateOf(RegisterFormValidState())
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                content = {
                    item {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Red
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_username),
                            value = username,
                            onValueChange = { it, valid ->
                                username = it
                                formState = formState.copy(usernameValidNow = valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            regex = "^[a-zA-Z0-9_]{3,}$",
                            errorText = stringResource(id = R.string.message_username_invalid)
                        )
                    }
                    item {
                        FormField(
                            title = stringResource(id = R.string.hint_email),
                            value = email,
                            onValueChange = { it, valid ->
                                email = it
                                formState = formState.copy(emailValidNow = valid)
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
                                formState = formState.copy(passwordValidNow = valid)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            errorText = stringResource(id = R.string.message_password_requirements),
                            regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
                        )
                    }
                    item {
                        if (showPolicy) {
                            val agreeTitleText = buildAnnotatedString {
                                append(stringResource(id = R.string.agree_with_policy_title_first_part))

                                append(" ")

                                pushStringAnnotation(
                                    tag = "policy",
                                    annotation = "https://test.test/some_policy"
                                )
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(stringResource(id = R.string.agree_with_policy_title_second_part))
                                }
                                pop()
                            }

                            val uriHandler = LocalUriHandler.current

                            ClickableText(
                                text = agreeTitleText,
                                style = MaterialTheme.typography.bodyMedium,
                                onClick = { offset ->
                                    agreeTitleText.getStringAnnotations(
                                        tag = "policy",
                                        start = offset,
                                        end = offset
                                    ).firstOrNull()?.let {
                                        uriHandler.openUri(it.item)
                                    }
                                },
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                onSend(
                                    email,
                                    username,
                                    password
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4EFF)),
                            enabled = formState.isValid()
                        )
                        {
                            Text(
                                text = actionName,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}
