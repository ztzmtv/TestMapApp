package com.example.mapapp.helper

import android.annotation.SuppressLint
import com.google.android.material.slider.Slider

class PanelSliderTouchListener(
    val onChangeSliderValue: (slider: Slider) -> Unit
) : Slider.OnSliderTouchListener {
    @SuppressLint("RestrictedApi") //исправят в новой версии Material components
    override fun onStartTrackingTouch(slider: Slider) {

    }

    @SuppressLint("RestrictedApi")
    override fun onStopTrackingTouch(slider: Slider) {
        onChangeSliderValue(slider)
    }
}