package com.example.formapp.ui.form.Login

data class LoginState(
    var isLoading : Boolean = false,
    var success : Int = -1,
    //var loginList : List<LoginModel.LoginJSON> = emptyList(),
    var error : String = "",
    var internet : Boolean = false
)