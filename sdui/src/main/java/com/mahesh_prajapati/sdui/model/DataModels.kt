package com.mahesh_prajapati.sdui.model

import com.google.gson.annotations.SerializedName

/**
 * Root response from JSONBin API
 */
data class JsonBinResponse(
    @SerializedName("record")
    val record: UIScreenData,
    @SerializedName("metadata")
    val metadata: Metadata
)

data class Metadata(
    @SerializedName("id")
    val id: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("createdAt")
    val createdAt: String
)

/**
 * Main screen data structure
 */
data class UIScreenData(
    @SerializedName("schemaVersion")
    val schemaVersion: Int,
    @SerializedName("minAppVersion")
    val minAppVersion: Int,
    @SerializedName("screen")
    val screen: ScreenInfo,
    @SerializedName("layout")
    val layout: LayoutConfig,
    @SerializedName("components")
    val components: List<UIComponent>,
    @SerializedName("analytics")
    val analytics: Analytics? = null,
    @SerializedName("fallback")
    val fallback: Fallback? = null
)

data class ScreenInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)

data class LayoutConfig(
    @SerializedName("type")
    val type: String,
    @SerializedName("padding")
    val padding: Int,
    @SerializedName("backgroundColor")
    val backgroundColor: String
)

/**
 * UI Component
 */
data class UIComponent(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("data")
    val data: ComponentData,
    @SerializedName("validation")
    val validation: List<ValidationRule>? = null,
    @SerializedName("action")
    val action: ComponentAction? = null
)

/**
 * Component Data - Can be different based on component type
 */
data class ComponentData(
    // Text component
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("textStyle")
    val textStyle: String? = null,
    @SerializedName("textColor")
    val textColor: String? = null,
    @SerializedName("clickable")
    val clickable: Boolean? = null,
    
    // Input component
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("hint")
    val hint: String? = null,
    @SerializedName("keyboardType")
    val keyboardType: String? = null,
    @SerializedName("required")
    val required: Boolean? = null,
    @SerializedName("secure")
    val secure: Boolean? = null,
    
    // Button component
    @SerializedName("style")
    val style: String? = null,
    @SerializedName("enabledWhenValid")
    val enabledWhenValid: Boolean? = null,
    
    // Image component
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("contentDescription")
    val contentDescription: String? = null
)

/**
 * Validation Rules
 */
data class ValidationRule(
    @SerializedName("rule")
    val rule: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("value")
    val value: Any? = null
)

/**
 * Component Actions
 */
data class ComponentAction(
    @SerializedName("type")
    val type: String,
    @SerializedName("method")
    val method: String? = null,
    @SerializedName("endpoint")
    val endpoint: String? = null,
    @SerializedName("body")
    val body: Map<String, String>? = null,
    @SerializedName("onSuccess")
    val onSuccess: ActionResult? = null,
    @SerializedName("onError")
    val onError: ActionResult? = null,
    @SerializedName("destination")
    val destination: String? = null
)

data class ActionResult(
    @SerializedName("type")
    val type: String,
    @SerializedName("destination")
    val destination: String? = null,
    @SerializedName("message")
    val message: String? = null
)

/**
 * Analytics
 */
data class Analytics(
    @SerializedName("screenView")
    val screenView: String,
    @SerializedName("events")
    val events: List<AnalyticsEvent>? = null
)

data class AnalyticsEvent(
    @SerializedName("componentId")
    val componentId: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("name")
    val name: String
)

/**
 * Fallback
 */
data class Fallback(
    @SerializedName("type")
    val type: String,
    @SerializedName("message")
    val message: String
)

/**
 * Component Types
 */
object ComponentType {
    const val TEXT = "text"
    const val INPUT = "input"
    const val BUTTON = "button"
    const val IMAGE = "image"
    const val SPACER = "spacer"
}

/**
 * Validation Rule Types
 */
object ValidationRuleType {
    const val NOT_EMPTY = "not_empty"
    const val EMAIL_FORMAT = "email_format"
    const val MIN_LENGTH = "min_length"
    const val MAX_LENGTH = "max_length"
    const val PATTERN = "pattern"
}

/**
 * Action Types
 */
object ActionType {
    const val API = "api"
    const val NAVIGATE = "navigate"
    const val DEEPLINK = "deeplink"
    const val TOAST = "toast"
}

/**
 * Text Styles
 */
object TextStyleType {
    const val TITLE_LARGE = "titleLarge"
    const val TITLE_MEDIUM = "titleMedium"
    const val BODY_LARGE = "bodyLarge"
    const val BODY_MEDIUM = "bodyMedium"
    const val BODY_SMALL = "bodySmall"
}

/**
 * Button Styles
 */
object ButtonStyleType {
    const val PRIMARY = "primary"
    const val SECONDARY = "secondary"
    const val OUTLINED = "outlined"
    const val TEXT = "text"
}
