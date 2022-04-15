package com.example.mapapp.domain.entity

data class Item(

    var id: Int = DEFAULT_NO_ID,
    var imageResId: Int? = null,
    var text: String? = null,
    var group: String? = null,
    var isChecked: Boolean = DEFAULT_IS_CHECK,
    var isVisible: Boolean = DEFAULT_IS_VISIBLE,
    var opacity: Float = DEFAULT_OPACITY,
    var isExpanded: Boolean = DEFAULT_EXPANDED

) {
    companion object {
        const val DEFAULT_NO_ID = -1
        private const val DEFAULT_IS_CHECK = true
        private const val DEFAULT_IS_VISIBLE = true
        private const val DEFAULT_OPACITY = 1.0f
        private const val DEFAULT_EXPANDED = false
    }
}
