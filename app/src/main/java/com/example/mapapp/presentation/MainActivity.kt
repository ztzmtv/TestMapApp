package com.example.mapapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.databinding.ActivityMainBinding
import com.example.mapapp.presentation.adapter.PanelItemAdapter


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MapAppViewModel::class.java] }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDrawerLayout()
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomPanelListeners()
    }

    private fun setupBottomPanelListeners() {
        binding.includeLayout.btnAddItem.setOnClickListener {
            viewModel.addDefaultItem()
        }
    }

    private fun setupRecyclerView() {
        val panelRecyclerView = binding.rvPanel
        val panelItemAdapter = PanelItemAdapter()
        panelItemAdapter.onDetailsClickListener = {
            log("$it")
            it.isExpanded = !it.isExpanded
        }
        panelRecyclerView.adapter = panelItemAdapter
        viewModel.itemsList.observe(this) {
            panelItemAdapter.submitList(it)
        }
    }

    private fun setupDrawerLayout() {
        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity_TAG"
        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}
