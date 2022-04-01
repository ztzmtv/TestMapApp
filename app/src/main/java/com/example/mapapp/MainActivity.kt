package com.example.mapapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardView = findViewById<CardView>(R.id.cv_item_1)
        val popupImage = findViewById<ImageView>(R.id.iv_arrow_popup_1)
        val hiddenPart = findViewById<ConstraintLayout>(R.id.cl_invisible_part_1)
        popupImage.setOnClickListener {
            if (hiddenPart.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenPart.visibility = View.VISIBLE
                popupImage.setImageResource(R.drawable.ic_arrow_up)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenPart.visibility = View.GONE
                popupImage.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        val cardView2 = findViewById<CardView>(R.id.cv_item_2)
        val popupImage2 = findViewById<ImageView>(R.id.iv_arrow_popup_2)
        val hiddenPart2 = findViewById<ConstraintLayout>(R.id.cl_invisible_part_2)
        popupImage2.setOnClickListener {
            if (hiddenPart2.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView2, AutoTransition())
                hiddenPart2.visibility = View.VISIBLE
                popupImage2.setImageResource(R.drawable.ic_arrow_up)
            } else {
                TransitionManager.beginDelayedTransition(cardView2, AutoTransition())
                hiddenPart2.visibility = View.GONE
                popupImage2.setImageResource(R.drawable.ic_arrow_down)
            }
        }
    }
}