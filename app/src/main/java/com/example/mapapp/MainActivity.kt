package com.example.mapapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.entity.Entity
import com.example.mapapp.repository.Repository
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val repository = Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rootLayout = binding.root
        val scrollView = generateScrollView()
        val linearLayoutScrollContainer = generateLinearLayoutContainer()
        val list = repository.getTemplateList()

        for (item in list) {
            //create ViewGroups
            val panelItemVisible = inflatePanelItemVisible()
            val panelItemInvisible = inflatePanelItemInvisible()
            val linearLayoutContainer = generateLinearLayoutContainer()
            val materialCardView = generateCardView()

            //set views to containers
            linearLayoutContainer.addView(panelItemVisible)
            linearLayoutContainer.addView(panelItemInvisible)
            materialCardView.addView(linearLayoutContainer)
            linearLayoutScrollContainer.addView(materialCardView)

            //get child views
            val itemImage = panelItemVisible.getChildAt(VISIBLE_ID_IMAGE) as ImageView
            val itemText = panelItemVisible.getChildAt(VISIBLE_ID_TEXT) as TextView
            val itemImageEye = panelItemVisible.getChildAt(VISIBLE_ID_EYE) as ImageView
            val itemPopupArrow = panelItemVisible.getChildAt(VISIBLE_ID_ARROW) as ImageView
            val itemSwitch = panelItemVisible.getChildAt(VISIBLE_ID_SWITCH) as SwitchMaterial
            val itemInvOpacity = panelItemInvisible.getChildAt(INVISIBLE_ID_OPACITY) as TextView
            val itemInvSynchronized =
                panelItemInvisible.getChildAt(INVISIBLE_ID_SYNCHRONIZED) as TextView
            val itemInvSlider = panelItemInvisible.getChildAt(INVISIBLE_ID_SLIDER) as Slider
            bindItemsToViews(itemImage, item, itemText, itemSwitch, panelItemInvisible)

            //set listeners on child views
            itemInvSlider.addOnSliderTouchListener(
                onSliderTouchListener(itemInvSynchronized, itemInvOpacity)
            )
            itemPopupArrow.setOnClickListener {
                setPanelVisibility(panelItemInvisible, itemPopupArrow, itemText, itemImage)
            }
            panelItemVisible.setOnLongClickListener {
                setPanelItemsOpacity(panelItemVisible, panelItemInvisible, itemImageEye)
                true
            }
            panelItemInvisible.setOnLongClickListener {
                setPanelItemsOpacity(panelItemVisible, panelItemInvisible, itemImageEye)
                true
            }
        }
        scrollView.addView(linearLayoutScrollContainer)
        rootLayout.addView(scrollView)
    }

    private fun onSliderTouchListener(
        tvSynchronized: TextView,
        tvOpacity: TextView
    ) = object : Slider.OnSliderTouchListener {
        @SuppressLint("RestrictedApi")
        override fun onStartTrackingTouch(slider: Slider) {
        }

        @SuppressLint("RestrictedApi")
        override fun onStopTrackingTouch(slider: Slider) {
            tvSynchronized.text = "TODO"
            tvOpacity.text = slider.value.toString()
            //TODO()
        }
    }

    private fun setPanelItemsOpacity(
        panelItemVisible: ConstraintLayout,
        panelItemInvisible: ConstraintLayout,
        imageEye: ImageView
    ) {
        if (panelItemVisible.getChildAt(VISIBLE_ID_IMAGE).alpha != OPACITY_HALF) {
            setPanelItemOpacity(panelItemVisible, OPACITY_HALF)
            setPanelItemOpacity(panelItemInvisible, OPACITY_HALF)
            imageEye.visibility = View.VISIBLE
        } else {
            setPanelItemOpacity(panelItemVisible, OPACITY_FULL)
            setPanelItemOpacity(panelItemInvisible, OPACITY_FULL)
            imageEye.visibility = View.INVISIBLE
        }
    }

    private fun setPanelItemOpacity(panelItem: ConstraintLayout, opacityValue: Float) {
        for (i in 0 until panelItem.childCount) panelItem.getChildAt(i).alpha = opacityValue
    }

    private fun bindItemsToViews(
        image: ImageView,
        item: Entity,
        text: TextView,
        switch: SwitchMaterial,
        panelItemInvisible: ConstraintLayout
    ) {
        image.setImageResource(item.resId ?: R.drawable.ic_launcher_background)
        text.text = item.text
        switch.isChecked = item.isChecked
        panelItemInvisible.visibility = View.GONE
    }

    private fun setPanelVisibility(
        panelItemInvisible: ConstraintLayout,
        popupArrow: ImageView,
        text: TextView,
        image: ImageView
    ) {
        val currentTextColor = text.currentTextColor
        if (panelItemInvisible.visibility == View.GONE) {
            panelItemInvisible.visibility = View.VISIBLE
            popupArrow.setImageResource(R.drawable.ic_arrow_up)
            text.currentTextColor
            text.setTextColor(Color.GREEN) //TODO( изменить на нормальные цвета )
        } else {
            panelItemInvisible.visibility = View.GONE
            popupArrow.setImageResource(R.drawable.ic_arrow_down)
            text.setTextColor(currentTextColor)
        }
    }

    private fun inflatePanelItemInvisible() = LayoutInflater.from(this)
        .inflate(R.layout.panel_item_invisible, null) as ConstraintLayout

    private fun inflatePanelItemVisible() = LayoutInflater.from(this)
        .inflate(R.layout.panel_item_visible, null) as ConstraintLayout

    private fun generateLinearLayoutContainer(): LinearLayout {
        val linearLayoutContainer = LinearLayout(this)
        val linearLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return linearLayoutContainer.apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = linearLayoutParams
            id = View.generateViewId()
        }
    }

    private fun generateCardView(): MaterialCardView {
        val cardViewLayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val materialCardView = MaterialCardView(this)
        return materialCardView.apply {
            layoutParams = cardViewLayoutParams
            id = View.generateViewId()
        }
    }

    private fun generateScrollView(): ScrollView {
        val scrollView = ScrollView(this)
        val viewGroupLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        scrollView.layoutParams = viewGroupLayoutParams
        return scrollView
    }

    companion object {
        private const val VISIBLE_ID_IMAGE = 0
        private const val VISIBLE_ID_TEXT = 1
        private const val VISIBLE_ID_EYE = 2
        private const val VISIBLE_ID_ARROW = 3
        private const val VISIBLE_ID_SWITCH = 4

        private const val INVISIBLE_ID_OPACITY = 0
        private const val INVISIBLE_ID_SYNCHRONIZED = 1
        private const val INVISIBLE_ID_SLIDER = 2

        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
    }
}
