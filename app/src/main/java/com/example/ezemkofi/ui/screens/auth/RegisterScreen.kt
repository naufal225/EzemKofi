package com.example.ezemkofi.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ezemkofi.data.models.LoginRequest
import com.example.ezemkofi.data.models.RegisterRequest
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.SharedPrefsManager
import com.example.ezemkofi.ui.components.AuthTobBar
import com.example.ezemkofi.ui.navigation.Screen
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins
import com.example.ezemkofi.ui.viewmodel.AuthViewModel


@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val authResult by authViewModel.authResult.observeAsState()

    val context = LocalContext.current

    val sharedPrefsManager = SharedPrefsManager(context)

    LaunchedEffect(authResult) {
        when(authResult) {
            is NetworkResponse.ERROR -> {
                authViewModel.authResult.postValue(null)
                Toast.makeText(context, (authResult as NetworkResponse.ERROR).message, Toast.LENGTH_LONG).show()
            }
            NetworkResponse.LOADING -> {

            }
            is NetworkResponse.SUCCESS -> {
                authViewModel.authResult.postValue(null)
                sharedPrefsManager.saveName(username)
                sharedPrefsManager.saveToken((authResult as NetworkResponse.SUCCESS<String>).data)

                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG).show()
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) {inclusive = true}
                    launchSingleTop = true
                }
            }
            null -> {

            }
        }
    }

    Scaffold(
        topBar = {
            AuthTobBar()
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(EzemWhite)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )

                Text(
                    text = "Register yourself to became our member and enjoy all the benefits",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    value = username,
                    label = {
                        Text(
                            text = "Username",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onValueChange = {username = it},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = EzemGreen
                    )
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    value = fullName,
                    label = {
                        Text(
                            text = "Fullname",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onValueChange = {fullName = it},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = EzemGreen
                    )
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    value = password,
                    label = {
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onValueChange = {password = it},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = EzemGreen
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    value = confirmPassword,
                    label = {
                        Text(
                            text = "Confirm Password",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onValueChange = {confirmPassword = it},
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = EzemGreen
                    )
                )

                Button(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    onClick = {
                        if(username.isNotEmpty() && password.isNotEmpty() && (password == confirmPassword)) {
                            authViewModel.register(RegisterRequest(
                                email = email,
                                password = password,
                                fullname = fullName,
                                username = username
                            ))
                        } else {
                            Toast.makeText(context, "Your data is not valid", Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(EzemGreen)
                ) {
                    Text(
                        text = "SIGN UP",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontFamily = Poppins
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account?",
                        color = EzemGray,
                        fontSize = 14.sp,
                        fontFamily = Poppins
                    )

                    TextButton(
                        onClick = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route)
                            }
                        }
                    ) {
                        Text(
                            text = "Login",
                            color = EzemGreen,
                            fontSize = 14.sp,
                            fontFamily = Poppins
                        )
                    }
                }
            }
        }
    }

}
