package com.example.mapapp.presentation

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.databinding.GroupDividerBinding
import com.example.mapapp.databinding.PanelItemInvisibleBinding
import com.example.mapapp.databinding.PanelItemVisibleBinding
import com.example.mapapp.domain.entity.Item
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.sortedItemsList.observe(this) {
            dynamicallyCreateViews(it, binding.llContainer)
        }

        setDrawerLayout()
        setFabScrollListener()
        setBtnAddItemClickListener()
    }

    private fun setBtnAddItemClickListener() {
        binding.includeLayout.btnAddItem.setOnClickListener {
            viewModel.addDefaultItem()
            Log.d("MainActivity", "OK")
        }
    }

    private fun setDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private fun dynamicallyCreateViews(
        listItems: List<Item>,
        linearLayoutScrollContainer: LinearLayout,
    ) {
        var groupName: String? = null
        for (item in listItems) {
            //binding
            val bindingVisible = PanelItemVisibleBinding.inflate(layoutInflater)
            val bindingInvisible = PanelItemInvisibleBinding.inflate(layoutInflater)

            //create ViewGroups
            val panelItemVisible = bindingVisible.root
            val panelItemInvisible = bindingInvisible.root
            val linearLayoutContainer = generateLinearLayoutContainer()
            val materialCardView = generateCardView()

            groupName?.let {
                if (item.group != it) {
                    val groupDivider = GroupDividerBinding.inflate(layoutInflater)
                    groupDivider.tvGroupName.text = groupName ?: "Без названия"
                    linearLayoutContainer.addView(groupDivider.root)
                }
            }
            groupName = item.group


            //set views to containers
            linearLayoutContainer.addView(panelItemVisible)
            linearLayoutContainer.addView(panelItemInvisible)
            materialCardView.addView(linearLayoutContainer)
            linearLayoutScrollContainer.addView(materialCardView)

            //bind values
            bindItemsToViews(item, bindingVisible, bindingInvisible, panelItemInvisible)

            //set listeners on child views
            bindingVisible.ivArrowPopup.setOnClickListener {
                setPanelVisibility(
                    panelItemInvisible,
                    bindingVisible.ivArrowPopup,
                    bindingVisible.tvPanelItem,
                    bindingVisible.ivPanelItem,
                )
            }

            bindingVisible.tvPanelItem.setOnClickListener {
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
                changeItemPanelOpacity(
                    panelItemInvisible,
                    item,
                    bindingInvisible.tvSynchronizedAt,
                    bindingInvisible.tvOpacity
                )
            )
        }
    }

    private fun setFabScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.fab.visibility = View.VISIBLE
            binding.scrollView.setOnScrollChangeListener { view, _, _, _, _ ->
                val childView = binding.scrollView.getChildAt(0)
                val diff = childView.bottom - (view.height + view.scrollY)
                binding.fab.visibility = if (diff == 0) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        } else {
            binding.fab.visibility = View.GONE
        }
    }

    private fun bindItemsToViews(
        item: Item,
        bindingVisible: PanelItemVisibleBinding,
        bindingInvisible: PanelItemInvisibleBinding,
        panelItemInvisible: ConstraintLayout
    ) {
        bindingVisible.ivPanelItem.setImageResource(
            item.imageResId ?: DEFAULT_EMPTY_IMAGE_RES
        )
        bindingVisible.tvPanelItem.text = item.text
        bindingVisible.swPanelItem.isChecked = item.isChecked
        bindingInvisible.panelSlider.value = item.opacity
        val textOpacity = resources.getString(R.string.string_opacity)
        bindingInvisible.tvOpacity.text =
            String.format(textOpacity, ((item.opacity) * PERCENTS_MULTIPLIER).toInt().toString())
        val textSynchronized = resources.getString(R.string.string_synchronized_at)
        bindingInvisible.tvSynchronizedAt.text = String.format(textSynchronized, getCurrentDate())
        val textCountOfElements = resources.getString(R.string.string_count_of_elements)
        bindingInvisible.tvCountOfElements.text =
            String.format(
                textCountOfElements,
                (getCountOfElements(
                    bindingInvisible, bindingVisible
                )).toString()
            )
        setPanelOpacity(panelItemInvisible, item.opacity)
        panelItemInvisible.visibility = View.GONE
    }

    private fun getCountOfElements(
        bindingInvisible: PanelItemInvisibleBinding,
        bindingVisible: PanelItemVisibleBinding
    ) = bindingInvisible.root.childCount + bindingVisible.root.childCount

    private fun changeItemPanelOpacity(
        panelItemInvisible: ConstraintLayout,
        item: Item,
        tvSynchronized: TextView,
        tvOpacity: TextView
    ) = object : Slider.OnSliderTouchListener {
        @SuppressLint("RestrictedApi") //исправят в новой версии Material components
        override fun onStartTrackingTouch(slider: Slider) {
        }

        @SuppressLint("RestrictedApi")
        override fun onStopTrackingTouch(slider: Slider) {
            viewModel.opacityChange(item, slider.value)
            setPanelOpacity(panelItemInvisible, slider.value)
            val textSynchronized = resources.getString(R.string.string_synchronized_at)
            tvSynchronized.text = String.format(textSynchronized, getCurrentDate())
            val textOpacity = resources.getString(R.string.string_opacity)
            tvOpacity.text =
                String.format(
                    textOpacity,
                    ((slider.value) * PERCENTS_MULTIPLIER).toInt().toString()
                )
        }
    }

    private fun getCurrentDate(): String? {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
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
        setPanelOpacity(panelItemVisible, opacityValue)
        setPanelOpacity(panelItemInvisible, opacityValue)
    }

    private fun setPanelOpacity(
        panelItem: ConstraintLayout,
        opacityValue: Float
    ) {
        for (i in 0 until panelItem.childCount) panelItem.getChildAt(i).alpha =
            opacityValue
    }

    private fun setPanelVisibility(
        panelItemInvisible: ConstraintLayout,
        popupArrow: ImageView,
        tvPanelItem: TextView,
        ivPanelItem: ImageView,
    ) {
        if (panelItemInvisible.visibility == View.GONE) {
            panelItemInvisible.visibility = View.VISIBLE
            popupArrow.setImageResource(R.drawable.ic_arrow_up)
            val greenColor = ContextCompat.getColor(this, R.color.primaryLightColor)
            tvPanelItem.setTextColor(greenColor)
            tvPanelItem.typeface = Typeface.DEFAULT_BOLD
            ivPanelItem.setColorFilter(greenColor)
        } else {
            panelItemInvisible.visibility = View.GONE
            popupArrow.setImageResource(R.drawable.ic_arrow_down)
            val defaultColor = ContextCompat.getColor(this, R.color.primaryTextColor)
            tvPanelItem.setTextColor(defaultColor)
            tvPanelItem.typeface = Typeface.DEFAULT
            ivPanelItem.setColorFilter(defaultColor)
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
            radius = 0F
        }
    }

    companion object {
        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
        private const val PERCENTS_MULTIPLIER = 100
        private const val DEFAULT_EMPTY_IMAGE_RES = R.drawable.ic_launcher_background
    }
}
