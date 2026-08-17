package com.surgex.app.ui.screens.auth

// Add this import at the top
import com.surgex.app.utils.PhoneValidator

// In the RegisterScreen composable, update the phone field validation:
// Around line 168-172, change to:

/*
    SurgeXTextField(
        value = phone,
        onValueChange = { 
            phone = it
            errorMessage = null 
        },
        label = "Phone number (+27...) e.g +27821234567",
        keyboardType = KeyboardType.Phone,
        placeholder = "+27 82 123 4567"
    )
*/

// And update the validation logic (around line 212-220) to:
/*
    when {
        name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() ->
            errorMessage = "Please fill in all fields."
        !PhoneValidator.isValidSouthAfricanPhone(phone) ->
            errorMessage = "Please enter a valid South African phone (+27...)"
        password != confirmPassword ->
            errorMessage = "Passwords do not match."
        password.length < 6 ->
            errorMessage = "Password must be at least 6 characters."
        else -> {
            isLoading = true
            scope.launch {
                val formattedPhone = PhoneValidator.formatToInternational(phone)
                when (val result = authController.register(
                    name = name.trim(),
                    email = email.trim(),
                    phone = formattedPhone,
                    password = password,
                    role = role
                )) {
                    is AuthResult.Success -> {
                        onRegisterSuccess(formattedPhone)
                    }
                    is AuthResult.Error -> {
                        errorMessage = result.message
                        isLoading = false
                    }
                }
            }
        }
    }
*/
