package com.example.formapp.ui.form

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.formapp.R
import com.example.formapp.navigation.AppScreens
import com.example.formapp.ui.MainActivity4
import com.example.formapp.ui.form.Login.FormViewModel
import com.example.formapp.ui.form.Login.LoginUtils
import com.example.formapp.ui.form.Login.LoginViewModel
import com.example.formapp.ui.theme.AppTheme
import com.example.formapp.ui.theme.LoginScreenTheme
import com.example.formapp.ui.theme.RedVisne
import kotlinx.coroutines.launch
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InicioScrenn(navController: NavController, viewModel: LoginViewModel = hiltViewModel(), email: String? = null){

    val state = viewModel.state.value

    val context = LocalContext.current

    val intent = remember {
        Intent(Settings.ACTION_WIRELESS_SETTINGS)
    }

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val username = remember {
        mutableStateOf("")
    }

    if (email != null && email != "Null") {

        LaunchedEffect(key1 = Unit) {

            username.value = email

        }
    }

    val password = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val isErrorEmailIcon = remember {
        mutableStateOf(false)
    }

    val isErrorEmailMessage = remember {
        mutableStateOf("Null")
    }

    val isErrorPasswordMessage = remember {
        mutableStateOf("Null")
    }


    LaunchedEffect(scaffoldState.snackbarHostState) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is FormViewModel.UiEvent.ShowSnackBar -> event.message?.let {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.asString(context)
                    )
                }
            }
        }
    }


    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility_off)
    else
        painterResource(id = R.drawable.ic_visibility)


    when (state.success) {

        1 -> {
            LaunchedEffect(key1 = Unit) {

                navController.navigate(AppScreens.MenuPrincipalScreen.route) {

                    popUpTo("menu_screens") { inclusive = true }

                }
            }
        }
    }

    LoginScreenTheme {

        Scaffold(

            scaffoldState = scaffoldState,
            snackbarHost = {

                SnackbarHost(it) {
                    Snackbar(
                        backgroundColor = Color.Red,
                        contentColor = Color.White,
                        actionColor = Color.White,
                        snackbarData = it
                    )
                }

            },
            content = {

                Box(contentAlignment = Alignment.Center, modifier = androidx.compose.ui.Modifier.fillMaxSize()) {

                    Column {

                        Column(
                            modifier = androidx.compose.ui.Modifier
                                .weight(1.3f)
                                .fillMaxWidth()
                        ) {

                            GlideImage(

                                modifier = androidx.compose.ui.Modifier
                                    .fillMaxWidth(),

                                imageModel = R.drawable.logo,
                                contentScale = ContentScale.FillBounds,

                                )
                        }

                        Column(
                            modifier = androidx.compose.ui.Modifier
                                .weight(2.7f)
                                .fillMaxWidth()
                                .offset(y = -30.dp)
                                .background(
                                    color = Color.White,
                                    RoundedCornerShape(
                                        topStart = AppTheme.dimens.grid_5,
                                        topEnd = AppTheme.dimens.grid_5
                                    )
                                )
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.Center, modifier = androidx.compose.ui.Modifier
                                    .fillMaxWidth()
                                    .padding(top = AppTheme.dimens.grid_2_5)
                            ) {

                                Text(
                                    text = stringResource(R.string.Bienvenido),
                                    color = RedVisne,
                                    fontSize = 29.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = androidx.compose.ui.Modifier.fillMaxSize()
                            ) {

                                Column(modifier = androidx.compose.ui.Modifier) {

                                    OutlinedTextField(
                                        modifier = androidx.compose.ui.Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppTheme.dimens.grid_3_5,
                                                end = AppTheme.dimens.grid_3_5,
                                                top = AppTheme.dimens.grid_4
                                            ),
                                        value = username.value,
                                        onValueChange = { username.value = it },
                                        label = {
                                            Text(
                                                text = stringResource(R.string.username),
                                                color = Color.Black
                                            )
                                        },
                                        colors = if (!isErrorEmailIcon.value) TextFieldDefaults.outlinedTextFieldColors(

                                            backgroundColor = Color.White,
                                            textColor = Color.Black,
                                            leadingIconColor = RedVisne,
                                            cursorColor = RedVisne,
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray

                                        ) else TextFieldDefaults.outlinedTextFieldColors(

                                            backgroundColor = Color.White,
                                            textColor = Color.Black,
                                            leadingIconColor = RedVisne,
                                            cursorColor = RedVisne,
                                            focusedBorderColor = Color.Red,
                                            unfocusedBorderColor = Color.Red
                                        ),

                                        leadingIcon = {

                                            IconButton(onClick = {


                                            }) {

                                                Icon(
                                                    imageVector = Icons.Filled.Email,
                                                    contentDescription = "E-Mail İcon"
                                                )

                                            }
                                        },

                                        keyboardOptions = KeyboardOptions(

                                            keyboardType = KeyboardType.Email,
                                            imeAction = ImeAction.Next
                                        ),

                                        trailingIcon = {

                                            if (isErrorEmailIcon.value)
                                                Icon(
                                                    Icons.Filled.Warning,
                                                    contentDescription = "E-Mail Error Icon",
                                                    tint = MaterialTheme.colors.error
                                                )
                                        }
                                    )

                                    if (isErrorEmailIcon.value) {
                                        Text(
                                            text = isErrorEmailMessage.value,
                                            color = MaterialTheme.colors.error,
                                            style = MaterialTheme.typography.caption,
                                            modifier = androidx.compose.ui.Modifier.padding(
                                                top = AppTheme.dimens.grid_1,
                                                start = AppTheme.dimens.grid_3_5
                                            )
                                        )
                                    }

                                    OutlinedTextField(
                                        modifier = androidx.compose.ui.Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppTheme.dimens.grid_3_5,
                                                end = AppTheme.dimens.grid_3_5,
                                                top = AppTheme.dimens.grid_2
                                            ),
                                        value = password.value,
                                        onValueChange = { password.value = it },
                                        label = {
                                            Text(
                                                text = stringResource(R.string.password),
                                                color = Color.Black
                                            )
                                        },

                                        colors = if (isErrorPasswordMessage.value == "Null")

                                            TextFieldDefaults.outlinedTextFieldColors(
                                                backgroundColor = Color.White,
                                                textColor = Color.Black,
                                                leadingIconColor = RedVisne,
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray
                                            )
                                        else TextFieldDefaults.outlinedTextFieldColors(
                                            backgroundColor = Color.White,
                                            textColor = Color.Black,
                                            leadingIconColor = RedVisne,
                                            focusedBorderColor = Color.Red,
                                            unfocusedBorderColor = Color.Red
                                        ),

                                        leadingIcon = {

                                            IconButton(onClick = {

                                            }) {

                                                Icon(
                                                    imageVector = Icons.Filled.Lock,
                                                    contentDescription = "Password İcon"
                                                )

                                            }
                                        },

                                        trailingIcon = {

                                            IconButton(onClick = {

                                                passwordVisibility = !passwordVisibility

                                            }) {

                                                Icon(
                                                    painter = icon,
                                                    contentDescription = "Password İcon"
                                                )

                                            }

                                        },

                                        visualTransformation = if (passwordVisibility) VisualTransformation.None
                                        else PasswordVisualTransformation(),

                                        singleLine = true,

                                        keyboardOptions = KeyboardOptions(

                                            keyboardType = KeyboardType.Password,
                                            imeAction = ImeAction.Done,
                                        ),

                                        keyboardActions = KeyboardActions(

                                            onDone = {

                                                keyboardController?.hide()

                                            }
                                        )
                                    )

                                    if (isErrorPasswordMessage.value != "Null") {
                                        Text(
                                            text = isErrorPasswordMessage.value,
                                            color = MaterialTheme.colors.error,
                                            style = MaterialTheme.typography.caption,
                                            modifier = androidx.compose.ui.Modifier.padding(
                                                top = AppTheme.dimens.grid_1,
                                                start = AppTheme.dimens.grid_3_5
                                            )
                                        )
                                    }


                                    Row(
                                        modifier = androidx.compose.ui.Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = AppTheme.dimens.grid_3_5 * 2,
                                                end = AppTheme.dimens.grid_3_5 * 2,
                                                top = AppTheme.dimens.grid_1_5 * 2
                                            ),
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        Button(
                                            onClick = {

                                                when (LoginUtils().loginFormatValidation(
                                                    username.value,
                                                    password.value
                                                )) {

                                                    1 -> {

                                                        viewModel.getUserLogin(
                                                            //ConstantsO.LOGIN,
                                                            //ConstantsO.TYPETWO,
                                                            username.value,
                                                            password.value
                                                        )
                                                    }

                                                    2 -> {

                                                        isErrorEmailIcon.value = true
                                                        isErrorEmailMessage.value =
                                                            context.getString(R.string.introduceEmail)

                                                    }

                                                    3 -> {

                                                        isErrorEmailIcon.value = true
                                                        isErrorEmailMessage.value =
                                                            context.getString(R.string.email_corto)

                                                    }

                                                    4 -> {

                                                        isErrorEmailIcon.value = true
                                                        isErrorEmailMessage.value =
                                                            context.getString(R.string.email_format)

                                                    }

                                                    5 -> {

                                                        isErrorEmailIcon.value = false
                                                        isErrorEmailMessage.value = "Null"
                                                        isErrorPasswordMessage.value =
                                                            context.getString(R.string.introducePassword)

                                                    }
                                                }
                                            },
                                            shape = RoundedCornerShape(AppTheme.dimens.grid_4),
                                            modifier = androidx.compose.ui.Modifier
                                                .fillMaxWidth(),

                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = RedVisne,
                                                contentColor = Color.White
                                            )

                                        ) {

                                            Text(

                                                text = stringResource(R.string.login),
                                                fontSize = 18.sp,
                                                modifier = androidx.compose.ui.Modifier
                                                    .padding(
                                                        top = AppTheme.dimens.grid_1,
                                                        bottom = AppTheme.dimens.grid_1
                                                    )

                                            )
                                        }
                                    }

                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = androidx.compose.ui.Modifier.fillMaxWidth()
                                    ) {

                                        if (state.isLoading) {

                                           // LoadingAnimation(speed = 4f)


                                        }
                                    }
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top,
                                    modifier = androidx.compose.ui.Modifier
                                        .fillMaxWidth()
                                ) {

                                    Text(text = stringResource(R.string.noCuenta))

                                    Spacer(modifier = androidx.compose.ui.Modifier.padding(AppTheme.dimens.grid_0_5))

                                    Text(text = stringResource(R.string.SingUp),
                                        color = RedVisne,
                                        fontWeight = FontWeight.Bold,
                                        modifier = androidx.compose.ui.Modifier
                                            .clickable {

                                                scope.launch {

                                                    viewModel.clearViewModel()

                                                    context.startActivity(Intent(context, MainActivity4::class.java))/* navController.navigate(route = AppScreens.NavegarScreen.route)*/

                                                   /* navController.navigate(
                                                        AppScreens.FormScreen.route
                                                        //AppScreens.MenuPrincipalScreen.route
                                                    )*/

                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }

                    if (state.internet) {

                        LaunchedEffect(key1 = Unit) {

                            scope.launch {

                                val sb = scaffoldState.snackbarHostState.showSnackbar(
                                    context.getString(R.string.no_internet_connection),
                                    actionLabel = context.getString(R.string.ajustes),
                                    duration = SnackbarDuration.Long
                                )

                                if (sb == SnackbarResult.ActionPerformed) {

                                    context.startActivity(intent)

                                }
                            }
                        }
                    }
                }
            }
        )
    }
    
}


