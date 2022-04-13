package com.example.mapapp.presentation

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
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

        }

    }

    companion object {
        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
        private const val PERCENTS_MULTIPLIER = 100
        private const val DEFAULT_EMPTY_IMAGE_RES = R.drawable.ic_launcher_background
    }
}
