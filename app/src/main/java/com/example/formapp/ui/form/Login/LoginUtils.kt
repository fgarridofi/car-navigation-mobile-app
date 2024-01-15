package com.example.formapp.ui.form.Login

class LoginUtils {



    //Los valores de correo están vacíos 2
    // Longitud insuficiente del correo electrónico 3
    // Formato de correo inapropiado 4
    //Los valores de contraseña están vacíos 5

    fun loginFormatValidation(email: String, password: String): Int {

        if (email.trim().isNotEmpty()) {

            if (email.length > 5) {

                if (email.contains("@")) {

                    return if (password.trim().isNotEmpty()) {

                        1

                    } else {


                        5

                    }


                } else {

                    return 4

                }


            } else {

                return 3

            }

        } else {

            return 2

        }
    }
}