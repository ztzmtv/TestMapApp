package com.example.mapapp.domain.entity

data class Item(

    var id: Int = DEFAULT_NO_ID,
    var imageResourceId: Int? = null,
    var text: String? = null,
    var isPopup: Boolean = DEFAULT_IS_POPUP,
    var isChecked: Boolean = DEFAULT_IS_CHECK,
    var isVisible: Boolean = DEFAULT_IS_VISIBLE,
    var opacity: Float = DEFAULT_OPACITY

) {
    companion object {
        private const val DEFAULT_NO_ID = -1
        private const val DEFAULT_IS_POPUP = true
        private const val DEFAULT_IS_CHECK = true
        private const val DEFAULT_IS_VISIBLE = true
        private const val DEFAULT_OPACITY = 1.0f
    }
}
