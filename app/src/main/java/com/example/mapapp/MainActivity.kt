package com.example.mapapp

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
            val panelItemVisible = inflatePanelItemVisible()
            val panelItemInvisible = inflatePanelItemInvisible()
            val linearLayoutContainer = generateLinearLayoutContainer()
            val materialCardView = generateCardView()

            linearLayoutContainer.addView(panelItemVisible)
            linearLayoutContainer.addView(panelItemInvisible)
            materialCardView.addView(linearLayoutContainer)
            linearLayoutScrollContainer.addView(materialCardView)

            val image = panelItemVisible.getChildAt(CHILD_ID_IMAGE) as ImageView
            val text = panelItemVisible.getChildAt(CHILD_ID_TEXT) as TextView
            val popupArrow = panelItemVisible.getChildAt(CHILD_ID_ARROW) as ImageView
            val switch = panelItemVisible.getChildAt(CHILD_ID_SWITCH) as SwitchMaterial
            bindItemsToViews(image, item, text, switch, panelItemInvisible)
            setPopupArrowClickListener(popupArrow, panelItemInvisible)
        }
        scrollView.addView(linearLayoutScrollContainer)
        rootLayout.addView(scrollView)
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

    private fun setPopupArrowClickListener(
        popupArrow: ImageView,
        panelItemInvisible: ConstraintLayout
    ) {
        popupArrow.setOnClickListener {
            if (panelItemInvisible.visibility == View.GONE) {
                panelItemInvisible.visibility = View.VISIBLE
                popupArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                panelItemInvisible.visibility = View.GONE
                popupArrow.setImageResource(R.drawable.ic_arrow_down)
            }
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
        private const val CHILD_ID_IMAGE = 0
        private const val CHILD_ID_TEXT = 1
        private const val CHILD_ID_ARROW = 2
        private const val CHILD_ID_SWITCH = 3
    }
}
