package com.mahesh_prajapati.sdui.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahesh_prajapati.sdui.model.ButtonStyleType
import com.mahesh_prajapati.sdui.model.ComponentAction
import com.mahesh_prajapati.sdui.model.ComponentType
import com.mahesh_prajapati.sdui.model.TextStyleType
import com.mahesh_prajapati.sdui.model.UIComponent

@Composable
fun DynamicComponent(
    component: UIComponent,
    fieldValue: String = "",
    fieldError: String? = null,
    onValueChange: (String) -> Unit = {},
    onAction: (ComponentAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when (component.type) {
        ComponentType.TEXT -> TextComponent(
            component = component,
            onAction = onAction,
            modifier = modifier
        )
        ComponentType.INPUT -> InputComponent(
            component = component,
            value = fieldValue,
            error = fieldError,
            onValueChange = onValueChange,
            modifier = modifier
        )
        ComponentType.BUTTON -> ButtonComponent(
            component = component,
            onAction = onAction,
            modifier = modifier
        )
        ComponentType.SPACER -> SpacerComponent(
            component = component,
            modifier = modifier
        )
    }
}

@Composable
fun TextComponent(
    component: UIComponent,
    onAction: (ComponentAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val data = component.data
    val textColor = parseColor(data.textColor)
    
    Text(
        text = data.text ?: "",
        style = getTextStyle(data.textStyle),
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (data.clickable == true && component.action != null) {
                    Modifier.clickable { onAction(component.action) }
                } else {
                    Modifier
                }
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputComponent(
    component: UIComponent,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val data = component.data
    var passwordVisible by remember { mutableStateOf(false) }
    
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(data.label ?: "") },
            placeholder = { Text(data.hint ?: "") },
            singleLine = true,
            isError = error != null,
            visualTransformation = if (data.secure == true && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = when (data.keyboardType) {
                    "email" -> KeyboardType.Email
                    "password" -> KeyboardType.Password
                    "number" -> KeyboardType.Number
                    "phone" -> KeyboardType.Phone
                    else -> KeyboardType.Text
                }
            ),
            trailingIcon = {
                if (data.secure == true) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) 
                                Icons.Default.Visibility 
                            else 
                                Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) 
                                "Hide password" 
                            else 
                                "Show password"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error
            )
        )
        
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ButtonComponent(
    component: UIComponent,
    onAction: (ComponentAction) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val data = component.data
    
    Button(
        onClick = {
            component.action?.let { onAction(it) }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled && !isLoading,
        colors = getButtonColors(data.style)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = data.text ?: "",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun SpacerComponent(
    component: UIComponent,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(16.dp))
}

// Helper functions
@Composable
private fun getTextStyle(textStyle: String?): TextStyle {
    return when (textStyle) {
        TextStyleType.TITLE_LARGE -> MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        TextStyleType.TITLE_MEDIUM -> MaterialTheme.typography.headlineMedium
        TextStyleType.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TextStyleType.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TextStyleType.BODY_SMALL -> MaterialTheme.typography.bodySmall
        else -> MaterialTheme.typography.bodyMedium
    }
}

@Composable
private fun getButtonColors(style: String?): ButtonColors {
    return when (style) {
        ButtonStyleType.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        ButtonStyleType.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
        ButtonStyleType.OUTLINED -> ButtonDefaults.outlinedButtonColors()
        else -> ButtonDefaults.buttonColors()
    }
}

private fun parseColor(colorString: String?): Color {
    return try {
        if (colorString == null) return Color.Unspecified
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        Color.Unspecified
    }
}
