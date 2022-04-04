package com.example.mapapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.databinding.PanelItemInvisibleBinding
import com.example.mapapp.databinding.PanelItemVisibleBinding
import com.example.mapapp.entity.Item
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.itemsList.observe(this) {
            dynamicallyCreateViews(it, binding.llContainer)
        }
    }

    private fun dynamicallyCreateViews(
        listItems: List<Item>,
        linearLayoutScrollContainer: LinearLayout,
    ) {
        for (item in listItems) {
            val bindingVisible = PanelItemVisibleBinding.inflate(layoutInflater)
            val bindingInvisible = PanelItemInvisibleBinding.inflate(layoutInflater)

            //create ViewGroups
            val panelItemVisible = bindingVisible.root
            val panelItemInvisible = bindingInvisible.root
            val linearLayoutContainer = generateLinearLayoutContainer()
            val materialCardView = generateCardView()

            //set views to containers
            linearLayoutContainer.addView(panelItemVisible)
            linearLayoutContainer.addView(panelItemInvisible)
            materialCardView.addView(linearLayoutContainer)
            linearLayoutScrollContainer.addView(materialCardView)

            //get child views
            bindItemsToViews(
                item,
                bindingVisible.ivPanelItem,
                bindingVisible.tvPanelItem,
                bindingVisible.swPanelItem,
                panelItemInvisible
            )

            //set listeners on child views
            bindingInvisible.panelSlider.addOnSliderTouchListener(
                onSliderTouchListener(
                    bindingInvisible.tvSynchronizedAt,
                    bindingInvisible.tvOpacity
                )
            )
            bindingVisible.ivArrowPopup.setOnClickListener {
                setPanelVisibility(
                    panelItemInvisible,
                    bindingVisible.ivArrowPopup,
                    bindingVisible.tvPanelItem,
                    bindingVisible.ivPanelItem
                )
            }
            panelItemVisible.setOnLongClickListener {
                setPanelItemsOpacity(panelItemVisible, panelItemInvisible, bindingVisible.ivEye)
                true
            }
            panelItemInvisible.setOnLongClickListener {
                setPanelItemsOpacity(panelItemVisible, panelItemInvisible, bindingVisible.ivEye)
                true
            }
        }
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
        item: Item,
        image: ImageView,
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

    private fun generateLinearLayoutContainer(): LinearLayout {
        val linearLayoutContainer = LinearLayout(this)
        val linearLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return linearLayoutContainer.apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = linearLayoutParams
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
        }
    }

    companion object {
        private const val VISIBLE_ID_IMAGE = 0
        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
    }
}
