package com.example.mapapp.presentation

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
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.databinding.PanelItemInvisibleBinding
import com.example.mapapp.databinding.PanelItemVisibleBinding
import com.example.mapapp.domain.entity.Item
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
            //binding
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
            bindingVisible.ivArrowPopup.setOnClickListener {
                setPanelVisibility(
                    panelItemInvisible,
                    bindingVisible.ivArrowPopup,
                    bindingVisible.tvPanelItem,
                    bindingVisible.ivPanelItem
                )
            }

            bindingVisible.swPanelItem.setOnCheckedChangeListener { _, switchValue ->
                viewModel.visibilityChange(item, switchValue)
                setPanelItemsOpacity(
                    panelItemVisible,
                    panelItemInvisible,
                    bindingVisible.ivEye,
                    switchValue
                )
            }

            bindingInvisible.panelSlider.addOnSliderTouchListener(
                onSliderTouchListener(
                    item,
                    bindingInvisible.tvSynchronizedAt,
                    bindingInvisible.tvOpacity
                )
            )
        }
    }

    private fun onSliderTouchListener(
        item: Item,
        tvSynchronized: TextView,
        tvOpacity: TextView
    ) = object : Slider.OnSliderTouchListener {
        @SuppressLint("RestrictedApi") //исправят в новой версии Material components
        override fun onStartTrackingTouch(slider: Slider) {
        }

        @SuppressLint("RestrictedApi")
        override fun onStopTrackingTouch(slider: Slider) {
            viewModel.opacityChange(item, slider.value.toInt())
            tvSynchronized.text = "TODO"//TODO дата синхронизации
            tvOpacity.text = slider.value.toString()
            //TODO()
        }
    }

    private fun setPanelItemsOpacity(
        panelItemVisible: ConstraintLayout,
        panelItemInvisible: ConstraintLayout,
        imageEye: ImageView,
        value: Boolean
    ) {
        if (!value) {
            setPanelsOpacity(panelItemVisible, panelItemInvisible, OPACITY_HALF)
            imageEye.visibility = View.VISIBLE
        } else {
            setPanelsOpacity(panelItemVisible, panelItemInvisible, OPACITY_FULL)
            imageEye.visibility = View.INVISIBLE
        }
    }

    private fun setPanelsOpacity(
        panelItemVisible: ConstraintLayout,
        panelItemInvisible: ConstraintLayout,
        opacityValue: Float
    ) {
        for (i in 0 until panelItemVisible.childCount) panelItemVisible.getChildAt(i).alpha =
            opacityValue
        for (i in 0 until panelItemVisible.childCount) panelItemInvisible.getChildAt(i).alpha =
            opacityValue
    }

    private fun bindItemsToViews(
        item: Item,
        imageView: ImageView,
        textView: TextView,
        switchMaterial: SwitchMaterial,
        constraintLayout: ConstraintLayout
    ) {
        imageView.setImageResource(item.resId ?: R.drawable.ic_launcher_background)
        textView.text = item.text
        switchMaterial.isChecked = item.isChecked
        constraintLayout.visibility = View.GONE
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
        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
    }
}