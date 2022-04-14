package com.example.mapapp.presentation

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.databinding.PanelItemInvisibleBinding
import com.example.mapapp.presentation.adapter.PanelItemAdapter
import com.example.mapapp.presentation.adapter.PanelListAdapter


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val rv = binding.rvPanel
        rv.layoutManager = layoutManager

        viewModel.sortedItemsList.observe(this) {
            val panelItemAdapter = PanelItemAdapter(it, this)

            panelItemAdapter.onDetailsClickListener = {
//                val b = PanelItemInvisibleBinding.inflate(layoutInflater)
//                layoutManager.addView(b.root)
            }
            rv.adapter = panelItemAdapter
        }
    }

    companion object {
        private const val OPACITY_FULL = 1.0f
        private const val OPACITY_HALF = 0.5f
        private const val PERCENTS_MULTIPLIER = 100
        private const val DEFAULT_EMPTY_IMAGE_RES = R.drawable.ic_launcher_background
    }
}
